package aspire.service.external;

import aspire.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TenderHubClientIntegrationTest {

//    private static final String INN = "7725362716";
//
//    @Autowired
//    private SystemConstantService systemConstantService;
//
//    @Autowired
//    private TenderHubClient tenderHubClient;
//
//    @Test(expected = HttpClientErrorException.class)
//    public void shouldThrowWhenRequestingUsersLoginUsingInvalidEndpoint() throws Exception {
//        String url = systemConstantService.findTenderHubApiUrl().getValue();
//        String login = systemConstantService.findTenderHubApiAuthLogin().getValue();
//        String password = systemConstantService.findTenderHubApiAuthPassword().getValue();
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + "/Invalid",
//                auth);
//
//        assertFalse(optionalUsersLoginResponse.isPresent());
//    }
//
//    @Test
//    public void shouldAllowRequestingUsersLoginUsingInvalidCredentials() throws Exception {
//        String url = systemConstantService.findTenderHubApiUrl().getValue();
//        String login = "invalid";
//        String password = "****";
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + systemConstantService.findTenderHubApiAuthEndpoint().getValue(),
//                auth);
//
//        assertTrue(optionalUsersLoginResponse.isPresent());
//        optionalUsersLoginResponse.ifPresent((UsersLoginResponse response) -> {
//            assertNull(response.getData());
//
//            assertThat(response.getCode(), is(500));
//
//            assertNotNull(response.getError());
//            assertNotNull(response.getError().getHeader());
//            assertNotNull(response.getError().getMessages());
//        });
//    }
//
//    @Test
//    public void shouldAllowPerformingUsersLoginRequest() throws Exception {
//        String url = systemConstantService.findTenderHubApiUrl().getValue();
//        String login = systemConstantService.findTenderHubApiAuthLogin().getValue();
//        String password = systemConstantService.findTenderHubApiAuthPassword().getValue();
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + systemConstantService.findTenderHubApiAuthEndpoint().getValue(),
//                auth);
//
//        assertTrue(optionalUsersLoginResponse.isPresent());
//        optionalUsersLoginResponse.ifPresent((UsersLoginResponse response) -> {
//            assertNotNull(response.getData());
//            assertThat(response.getData().getAccess(), not(isEmptyString()));
//            assertThat(response.getData().getRefresh(), not(isEmptyString()));
//        });
//    }
//
//    @Test
//    public void shouldAllowPerformingSparkGetCompanyExtendedReportRequest() throws Exception {
//        String url = systemConstantService.findTenderHubApiUrl().getValue();
//        String login = systemConstantService.findTenderHubApiAuthLogin().getValue();
//        String password = systemConstantService.findTenderHubApiAuthPassword().getValue();
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + systemConstantService.findTenderHubApiAuthEndpoint().getValue(),
//                auth);
//
//        assertTrue(optionalUsersLoginResponse.isPresent());
//
//        UsersLoginResponse usersLoginResponse = optionalUsersLoginResponse.get();
//        auth.setAccessToken(usersLoginResponse.getData().getAccess());
//        auth.setRefreshToken(usersLoginResponse.getData().getRefresh());
//
//        Optional<SparkGetCompanyExtendedReportResponse> optionalSparkGetCompanyExtendedReportResponse = tenderHubClient.getSparkGetCompanyExtendedReport(
//                url + "/Spark/GetCompanyExtendedReport/" + INN,
//                auth);
//
//        assertTrue(optionalSparkGetCompanyExtendedReportResponse.isPresent());
//        optionalSparkGetCompanyExtendedReportResponse.ifPresent((SparkGetCompanyExtendedReportResponse response) -> {
//            assertThat(response.getCode(), is(200));
//            assertThat(response.getData(), is(notNullValue()));
//            assertThat(response.getError(), is(nullValue()));
//        });
//    }
//
//    @Test
//    public void shouldAllowPerformingSparkGetCompanyExecutionProceedingsRequest() throws Exception { // FIXME:
//        String url = systemConstantService.findTenderHubApiUrl().getValue();
//        String login = systemConstantService.findTenderHubApiAuthLogin().getValue();
//        String password = systemConstantService.findTenderHubApiAuthPassword().getValue();
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + systemConstantService.findTenderHubApiAuthEndpoint().getValue(),
//                auth);
//
//        assertTrue(optionalUsersLoginResponse.isPresent());
//
//        UsersLoginResponse usersLoginResponse = optionalUsersLoginResponse.get();
//        auth.setAccessToken(usersLoginResponse.getData().getAccess());
//        auth.setRefreshToken(usersLoginResponse.getData().getRefresh());
//
//        Optional<SparkGetCompanyExecutionProceedingsResponse> optionalSparkGetCompanyExecutionProceedingsResponse =
//                tenderHubClient.getSparkGetCompanyExecutionProceedings(
//                        url + "/Spark/GetCompanyExecutionProceedings/" + INN,
//                        auth);
//
//        assertTrue(optionalSparkGetCompanyExecutionProceedingsResponse.isPresent());
//        optionalSparkGetCompanyExecutionProceedingsResponse.ifPresent((SparkGetCompanyExecutionProceedingsResponse response) -> {
//
//            System.out.printf("RESPONSE STRING: %s", response);
//            assertThat(response, is(notNullValue()));
//        });
//    }
//
//    @Test
//    public void shouldAllowPerformingSparkGetCompanyStructureRequest() throws Exception { // FIXME:
//        String url = systemConstantService.findTenderHubApiUrl().getValue();
//        String login = systemConstantService.findTenderHubApiAuthLogin().getValue();
//        String password = systemConstantService.findTenderHubApiAuthPassword().getValue();
//
//        Auth auth = new Auth();
//        auth.setLogin(login);
//        auth.setPassword(password);
//
//        Optional<UsersLoginResponse> optionalUsersLoginResponse = tenderHubClient.postUsersLogin(
//                url + systemConstantService.findTenderHubApiAuthEndpoint().getValue(),
//                auth);
//
//        assertTrue(optionalUsersLoginResponse.isPresent());
//
//        UsersLoginResponse usersLoginResponse = optionalUsersLoginResponse.get();
//        auth.setAccessToken(usersLoginResponse.getData().getAccess());
//        auth.setRefreshToken(usersLoginResponse.getData().getRefresh());
//
//        Optional<SparkGetCompanyStructureResponse> optionalSparkGetCompanyStructureResponse =
//                tenderHubClient.getSparkGetCompanyStructure(
//                        url + "/Spark/GetCompanyStructure/" + INN,
//                        auth);
//
//        assertTrue(optionalSparkGetCompanyStructureResponse.isPresent());
//        optionalSparkGetCompanyStructureResponse.ifPresent((SparkGetCompanyStructureResponse response) -> {
//
//            System.out.printf("RESPONSE STRING: %s", response);
//            assertThat(response, is(notNullValue()));
//        });
//    }

}
