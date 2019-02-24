package aspire.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class InputOrganization {

    @NotNull
    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
