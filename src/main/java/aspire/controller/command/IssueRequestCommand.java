package aspire.controller.command;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class IssueRequestCommand {

    @NotNull
    @NotEmpty
    private String inn;

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

}
