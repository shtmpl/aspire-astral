package aspire.astral.controller.response;

import aspire.astral.domain.Employment;

import java.util.Date;
import java.util.List;

public class ResponseVacancy {

    private String id;
    private String origin;
    private Date dateCreated;
    private Date datePublished;
    private String title;
    private String description;
    private ResponseSalary salary;
    private Employment employment;
    private ResponseEmployer employer;
    private List<ResponseVacancyContact> contacts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

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

    public ResponseSalary getSalary() {
        return salary;
    }

    public void setSalary(ResponseSalary salary) {
        this.salary = salary;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public ResponseEmployer getEmployer() {
        return employer;
    }

    public void setEmployer(ResponseEmployer employer) {
        this.employer = employer;
    }

    public List<ResponseVacancyContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<ResponseVacancyContact> contacts) {
        this.contacts = contacts;
    }
}
