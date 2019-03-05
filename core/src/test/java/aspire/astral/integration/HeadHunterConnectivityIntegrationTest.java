package aspire.astral.integration;

import aspire.astral.Application;
import aspire.astral.config.HeadHunterProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HeadHunterConnectivityIntegrationTest {

    @Autowired
    private HeadHunterProperties headHunterProperties;

    @Test
    public void shouldAllowAccessingVacanciesEndpoint() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(headHunterProperties.getUrl())
                .path(HeadHunterClient.PATH_VACANCIES)
                .queryParam(HeadHunterClient.QUERY_PARAM_PAGE, 0)
                .queryParam(HeadHunterClient.QUERY_PARAM_SIZE, 10);

        RestAssured.given()
                .header("User-Agent", headHunterProperties.getHeaders().get("User-Agent"))
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(builder.toUriString())
                .then().log().all()
                .statusCode(not(404));
    }

    @Test
    public void shouldAllowAccessingVacancyByIdEndpoint() {
        RestAssured.given()
                .header("User-Agent", headHunterProperties.getHeaders().get("User-Agent"))
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(headHunterProperties.getUrl() + HeadHunterClient.PATH_VACANCY_BY_ID.replace("{id}", "42"))
                .then().log().all()
                .statusCode(not(404));
    }
}
