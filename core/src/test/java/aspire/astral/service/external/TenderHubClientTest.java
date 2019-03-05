package aspire.astral.service.external;

import aspire.astral.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockRestServiceServer
public class TenderHubClientTest {

//    @Autowired
//    private TenderHubClient tenderHubClient;
//
//    @Autowired
//    private MockRestServiceServer server;
//
//    @Test
//    public void shouldAllowPerformingUsersLoginRequest() throws Exception {
//        String url = "http://localhost:8080/api";
//        String login = "root@example.com";
//        String password = "****";
//
//        server.expect(requestTo(url + "/Users/Login"))
//                .andExpect(method(HttpMethod.POST))
//                .andExpect(jsonPath("email", equalTo(login)))
//                .andExpect(jsonPath("password", equalTo(password)))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body("{ \"data\": { \"access\": \"All your base are belong to us\" } }"));
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + "/Users/Login",
//                auth);
//
//        server.verify();
//
//        assertTrue(optionalUsersLoginResponse.isPresent());
//        optionalUsersLoginResponse.ifPresent((UsersLoginResponse response) -> {
//            assertNotNull(response);
//            assertNotNull(response.getData());
//
//            UsersLoginResponse.Data data = response.getData();
//            assertThat(data.getAccess(), is("All your base are belong to us"));
//        });
//    }
//
//    @Test
//    public void shouldAllowPerformingSparkGetCompanyExtendedReportRequest() throws Exception {
//        String url = "http://localhost:8080/api";
//        String login = "me@example.com";
//        String password = "****";
//
//        server.expect(requestTo(url + "/Users/Login"))
//                .andExpect(method(HttpMethod.POST))
//                .andExpect(jsonPath("email", equalTo(login)))
//                .andExpect(jsonPath("password", equalTo(password)))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body("{ \"data\": { \"access\": \"All your base are belong to us\" } }"));
//
//        server.expect(requestTo(url + "/Spark/SparkGetCompanyExtendedReport/42"))
//                .andExpect(method(HttpMethod.GET))
//                .andExpect(header("Authorization", equalTo("Bearer All your base are belong to us")))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body("{ \"data\": { \"expected\": \"Hello, World!\" } }"));
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + "/Users/Login",
//                auth);
//
//        assertTrue(optionalUsersLoginResponse.isPresent());
//        optionalUsersLoginResponse.ifPresent((UsersLoginResponse response) -> {
//            auth.setAccessToken(response.getData().getAccess());
//            auth.setRefreshToken(response.getData().getRefresh());
//        });
//
//        Optional<SparkGetCompanyExtendedReportResponse> optionalSparkGetCompanyExtendedReportResponse = tenderHubClient.getSparkGetCompanyExtendedReport(
//                url + "/Spark/SparkGetCompanyExtendedReport/42",
//                auth);
//
//        assertTrue(optionalSparkGetCompanyExtendedReportResponse.isPresent());
//        optionalSparkGetCompanyExtendedReportResponse.ifPresent((SparkGetCompanyExtendedReportResponse response) -> {
//            assertNotNull(response);
//            assertNotNull(response.getData());
//            // ...
//        });
//    }
}
