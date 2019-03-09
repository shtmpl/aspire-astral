package aspire.astral.integration;

import aspire.astral.config.HeadHunterProperties;
import aspire.astral.integration.response.ResponseVacancies;
import aspire.astral.integration.response.ResponseVacancy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class HeadHunterClient {

    static final String QUERY_PARAM_PAGE = "page";
    static final String QUERY_PARAM_SIZE = "per_page";

    static final String PATH_VACANCIES = "/vacancies";
    static final String QUERY_PARAM_SEARCH_FIELD = "search_field";
    static final String QUERY_PARAM_SEARCH_VALUE = "text";

    static final String PATH_VACANCY_BY_ID = "/vacancies/{id}";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HeadHunterProperties headHunterProperties;

    private final RestTemplate restTemplate;

    @Autowired
    public HeadHunterClient(HeadHunterProperties headHunterProperties,
                            RestTemplateBuilder restTemplateBuilder) {
        this.headHunterProperties = headHunterProperties;

        this.restTemplate = restTemplateBuilder
                .additionalInterceptors(new LoggingClientHttpRequestInterceptor())
                .build();
    }

    public Optional<ResponseVacancies> getVacancies(Pageable pageable) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(headHunterProperties.getUrl())
                .path(PATH_VACANCIES)
                .queryParam(QUERY_PARAM_PAGE, pageable.getPageNumber())
                .queryParam(QUERY_PARAM_SIZE, pageable.getPageSize());

        return requestVacancies(builder.toUriString());
    }

    public Optional<ResponseVacancies> getVacancies(String title, Pageable pageable) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(headHunterProperties.getUrl())
                .path(PATH_VACANCIES)
                .queryParam(QUERY_PARAM_SEARCH_FIELD, "name")
                .queryParam(QUERY_PARAM_SEARCH_VALUE, title)
                .queryParam(QUERY_PARAM_PAGE, pageable.getPageNumber())
                .queryParam(QUERY_PARAM_SIZE, pageable.getPageSize());

        return requestVacancies(builder.toUriString());
    }

    private Optional<ResponseVacancies> requestVacancies(String url) {
        try {
            ResponseEntity<ResponseVacancies> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders(headHunterProperties)),
                    ResponseVacancies.class);

            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        } catch (Exception exception) {
            logger.error("Error interacting with remote resource", exception);

            throw new IntegrationException(exception);
        }
    }

    public Optional<ResponseVacancy> getVacancy(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(headHunterProperties.getUrl())
                .path(PATH_VACANCY_BY_ID);

        return requestVacancy(builder.buildAndExpand(id).toUriString());
    }

    private Optional<ResponseVacancy> requestVacancy(String url) {
        try {
            ResponseEntity<ResponseVacancy> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders(headHunterProperties)),
                    ResponseVacancy.class);

            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        } catch (Exception exception) {
            logger.error("Error interacting with remote resource", exception);

            throw new IntegrationException(exception);
        }
    }

    private static HttpHeaders createHeaders(HeadHunterProperties properties) {
        HttpHeaders result = new HttpHeaders();
        result.set("User-Agent", properties.getHeaders().get("User-Agent"));

        return result;
    }
}
