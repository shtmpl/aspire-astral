package aspire.controller.command;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReadRequestCommand {

    @NotNull
    @NotEmpty
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
