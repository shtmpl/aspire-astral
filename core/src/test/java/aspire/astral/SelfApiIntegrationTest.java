package aspire.astral;

import aspire.astral.Application;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelfApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void apiMakeCompanyInfoRequestEndpointIsAvailable() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("http://localhost:" + port + "/vacancies")
                .then()
                .statusCode(not(equalTo(404)));
    }
}
