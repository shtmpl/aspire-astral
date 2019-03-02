package aspire.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class InputVacancy {

    @NotBlank
    private String title;

    private String description;

    @PositiveOrZero
    private BigDecimal salary;

    @NotNull
    private String employment;

    private InputOrganization inputOrganization;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public InputOrganization getInputOrganization() {
        return inputOrganization;
    }

    public void setInputOrganization(InputOrganization inputOrganization) {
        this.inputOrganization = inputOrganization;
    }

}
