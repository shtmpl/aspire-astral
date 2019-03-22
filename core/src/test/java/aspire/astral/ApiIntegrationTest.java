package aspire.astral;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Integration test for validating the endpoint availability exposed by this program.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

    private static final String PATH_VACANCY_INDEX = "/api/vacancy/{repository}/index";
    private static final String PATH_VACANCY_SEARCH = "/api/vacancy/{repository}/search?title.like={title}";

    @LocalServerPort
    private int port;

    @Test
    public void shouldAllowAccessingVacancyIndexEndpoint() {
        given().baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(PATH_VACANCY_INDEX, "local")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void shouldAllowAccessingVacancySearchEndpoint() {
        given().baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(PATH_VACANCY_SEARCH, "local", "java")
                .then().log().all()
                .statusCode(200);
    }
}
