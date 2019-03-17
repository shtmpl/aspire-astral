package aspire.astral.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class RequestVacancy {

    private Date datePublished;

    @NotBlank
    private String title;

    private String description;

    private RequestSalary salary;

    @NotNull
    private String employment;

    private RequestEmployer employer;

    private List<RequestVacancyContact> contacts;

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

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

    public RequestSalary getSalary() {
        return salary;
    }

    public void setSalary(RequestSalary salary) {
        this.salary = salary;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public RequestEmployer getEmployer() {
        return employer;
    }

    public void setEmployer(RequestEmployer employer) {
        this.employer = employer;
    }

    public List<RequestVacancyContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<RequestVacancyContact> contacts) {
        this.contacts = contacts;
    }
}
