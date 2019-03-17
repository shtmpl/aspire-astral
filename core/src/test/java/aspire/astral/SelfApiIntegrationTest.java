package aspire.astral;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelfApiIntegrationTest {

    private static final String PATH_VACANCY_INDEX = "/api/vacancy/index/{repository}";

    @LocalServerPort
    private int port;

    @Test
    public void shouldAllowRequestingVacancyIndex() {
        given().baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .body("{}")
                .when().log().all()
                .get(PATH_VACANCY_INDEX, "local")
                .then().log().all()
                .statusCode(200);
    }
}
