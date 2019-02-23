package aspire;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelfApiIntegrationTest {

//    private static final String ENDPOINT_MAKE_COMPANY_INFO_REQUEST = "/api/MakeCompanyInfoRequest";
//    private static final String ENDPOINT_CHECK_REQUEST_STATUS = "/api/CheckRequestStatus";
//    private static final String ENDPOINT_GET_COMPANY_INFO = "/api/GetCompanyInfo";
//
//    @LocalServerPort
//    private int port;
//
//    @Test
//    public void apiMakeCompanyInfoRequestEndpointIsAvailable() {
//        given()
//                .contentType(ContentType.JSON)
//                .body("{}")
//                .when()
//                .post("http://localhost:" + port + "/" + ENDPOINT_MAKE_COMPANY_INFO_REQUEST)
//                .then()
//                .statusCode(not(equalTo(404)));
//    }
//
//    @Test
//    public void apiCheckRequestStatusEndpointIsAvailable() {
//        given()
//                .contentType(ContentType.JSON)
//                .body("{}")
//                .when()
//                .post("http://localhost:" + port + "/" + ENDPOINT_CHECK_REQUEST_STATUS)
//                .then()
//                .statusCode(not(equalTo(404)));
//    }
//
//    @Test
//    public void apiGetCompanyInfoEndpointIsAvailable() {
//        given()
//                .contentType(ContentType.JSON)
//                .body("{}")
//                .when()
//                .post("http://localhost:" + port + "/" + ENDPOINT_GET_COMPANY_INFO)
//                .then()
//                .statusCode(not(equalTo(404)));
//    }

}
