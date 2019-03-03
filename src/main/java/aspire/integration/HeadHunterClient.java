package aspire.integration;

import aspire.config.HeadHunterProperties;
import aspire.integration.response.ResponseVacancies;
import aspire.integration.response.ResponseVacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class HeadHunterClient {

    static final String PATH_VACANCIES = "/vacancies";
    static final String PATH_VACANCY_BY_ID = "/vacancies/{id}";

    private final HeadHunterProperties headHunterProperties;

    private final RestTemplate restTemplate;

    @Autowired
    public HeadHunterClient(HeadHunterProperties headHunterProperties,
                            RestTemplateBuilder restTemplateBuilder) {
        this.headHunterProperties = headHunterProperties;

        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<ResponseVacancies> getVacancies() {
        ResponseEntity<ResponseVacancies> response = restTemplate.exchange(
                headHunterProperties.getUrl() + PATH_VACANCIES,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(headHunterProperties)),
                ResponseVacancies.class);

        return Optional.ofNullable(response.getBody());
    }

    public Optional<ResponseVacancy> getVacancy(String id) {
        ResponseEntity<ResponseVacancy> response = restTemplate.exchange(
                headHunterProperties.getUrl() + PATH_VACANCY_BY_ID,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(headHunterProperties)),
                ResponseVacancy.class,
                id);

        return Optional.ofNullable(response.getBody());
    }

    private static HttpHeaders createHeaders(HeadHunterProperties properties) {
        HttpHeaders result = new HttpHeaders();
        result.set("User-Agent", properties.getHeaders().get("User-Agent"));

        return result;
    }

}
