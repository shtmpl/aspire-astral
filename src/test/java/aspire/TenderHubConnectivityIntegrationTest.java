package aspire;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TenderHubConnectivityIntegrationTest {

//    private static final String INN = "7725362716";
//
//    private static final String ENDPOINT_USERS_LOGIN = "/Users/Login";
//
//    private static final String ENDPOINT_SPARK_GET_COMPANY_EXTENDED_REPORT = "/Spark/GetCompanyExtendedReport/" + INN;
//    private static final String ENDPOINT_SPARK_GET_COMPANY_EXECUTION_PROCEEDINGS = "/Spark/GetCompanyExecutionProceedings/" + INN;
//    private static final String ENDPOINT_SPARK_GET_COMPANY_STRUCTURE = "/Spark/GetCompanyStructure/" + INN;
//
//    @Autowired
//    private YandexApiProperties yandexApiProperties;
//
//    @Test
//    public void shouldAllowAccessingUsersLoginEndpoint() {
//        given()
//                .contentType(ContentType.JSON)
//                .body(String.format(
//                        "{ \"email\": \"%s\", \"password\": \"%s\" }",
//                        yandexApiProperties.getAuth().getLogin(),
//                        yandexApiProperties.getAuth().getPassword()))
//                .when().log().all()
//                .post(yandexApiProperties.getUrl() + ENDPOINT_USERS_LOGIN)
//                .then().log().all()
//                .statusCode(200)
//                .body("", hasKey("data"), "data", hasKey("access"), "data.access", not(isEmptyOrNullString()))
//                .body("", hasKey("code"), "code", equalTo(200))
//                .body("", hasKey("error"), "error", nullValue());
//    }
//
//    @Test
//    public void shouldAllowAccessingSparkGetCompanyExtendedReportEndpoint() {
//        String accessToken = given()
//                .contentType(ContentType.JSON)
//                .body(String.format(
//                        "{ \"email\": \"%s\", \"password\": \"%s\" }",
//                        yandexApiProperties.getAuth().getLogin(),
//                        yandexApiProperties.getAuth().getPassword()))
//                .when().log().all()
//                .post(yandexApiProperties.getUrl() + ENDPOINT_USERS_LOGIN)
//                .then().log().all()
//                .statusCode(200)
//                .extract()
//                .path("data.access");
//
//        given()
//                .header("Authorization", String.format("Bearer %s", accessToken))
//                .when().log().all()
//                .get(yandexApiProperties.getUrl() + ENDPOINT_SPARK_GET_COMPANY_EXTENDED_REPORT)
//                .then().log().all()
//                .statusCode(200)
//                .body("", hasKey("data"))
//                .body("", hasKey("code"), "code", equalTo(200))
//                .body("", hasKey("error"), "error", nullValue());
//    }
//
//    @Test
//    public void shouldAllowAccessingSparkGetCompanyExecutionProceedingsEndpoint() {
//        String accessToken = given()
//                .contentType(ContentType.JSON)
//                .body(String.format(
//                        "{ \"email\": \"%s\", \"password\": \"%s\" }",
//                        yandexApiProperties.getAuth().getLogin(),
//                        yandexApiProperties.getAuth().getPassword()))
//                .when().log().all()
//                .post(yandexApiProperties.getUrl() + ENDPOINT_USERS_LOGIN)
//                .then().log().all()
//                .statusCode(200)
//                .extract()
//                .path("data.access");
//
//        given()
//                .header("Authorization", String.format("Bearer %s", accessToken))
//                .when().log().all()
//                .get(yandexApiProperties.getUrl() + ENDPOINT_SPARK_GET_COMPANY_EXECUTION_PROCEEDINGS)
//                .then().log().all()
//                .statusCode(200)
//                .body("", hasKey("data"))
//                .body("", hasKey("code"), "code", equalTo(200))
//                .body("", hasKey("error"), "error", nullValue());
//    }
//
//    @Test
//    public void shouldAllowAccessingSparkGetCompanyStructureEndpoint() {
//        String accessToken = given()
//                .contentType(ContentType.JSON)
//                .body(String.format(
//                        "{ \"email\": \"%s\", \"password\": \"%s\" }",
//                        yandexApiProperties.getAuth().getLogin(),
//                        yandexApiProperties.getAuth().getPassword()))
//                .when().log().all()
//                .post(yandexApiProperties.getUrl() + ENDPOINT_USERS_LOGIN)
//                .then().log().all()
//                .statusCode(200)
//                .extract()
//                .path("data.access");
//
//        given()
//                .header("Authorization", String.format("Bearer %s", accessToken))
//                .when().log().all()
//                .get(yandexApiProperties.getUrl() + ENDPOINT_SPARK_GET_COMPANY_STRUCTURE)
//                .then().log().all()
//                .statusCode(200)
//                .body("", hasKey("data"))
//                .body("", hasKey("code"), "code", equalTo(200))
//                .body("", hasKey("error"), "error", nullValue());
//    }

}
