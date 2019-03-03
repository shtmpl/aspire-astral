package aspire.integration;

import aspire.Application;
import aspire.integration.response.ResponseVacancies;
import aspire.integration.response.ResponseVacancy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HeadHunterClientIntegrationTest {

    @Autowired
    private HeadHunterClient headHunterClient;

    @Test
    public void shouldAllowRequestingVacancies() throws Exception {
        Optional<ResponseVacancies> response = headHunterClient.getVacancies();

        assertTrue(response.isPresent());
        response.ifPresent((ResponseVacancies it) -> {
            assertThat(it.getItems(), is(notNullValue()));
            assertThat(it.getItems().size(), is(not(0)));
        });
    }

    @Test
    public void shouldAllowRequestingVacancyById() throws Exception {
        Optional<ResponseVacancy> response = headHunterClient.getVacancy("42");

        assertTrue(response.isPresent());
        response.ifPresent((ResponseVacancy it) -> {
            assertThat(it.getId(), is(notNullValue()));
            assertThat(it.getName(), is(notNullValue()));
            assertThat(it.getDescription(), is(notNullValue()));
        });
    }

}
