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

import static org.hamcrest.Matchers.*;

/**
 * Integration test for validating endpoint availability exposed by the external resource.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HeadHunterConnectivityIntegrationTest {

    private static final String PATH_VACANCIES = "/vacancies";
    private static final String PATH_VACANCY_BY_ID = "/vacancies/{id}";

    @Autowired
    private HeadHunterProperties headHunterProperties;

    @Test
    public void shouldAllowAccessingVacanciesEndpoint() {
        RestAssured.given()
                .baseUri(headHunterProperties.getUrl())
                .header("User-Agent", headHunterProperties.getHeaders().get("User-Agent"))
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(PATH_VACANCIES)
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void shouldAllowAccessingVacancyByIdEndpoint() {
        RestAssured.given()
                .baseUri(headHunterProperties.getUrl())
                .header("User-Agent", headHunterProperties.getHeaders().get("User-Agent"))
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(PATH_VACANCY_BY_ID, "42")
                .then().log().all()
                .statusCode(200);
    }
}
