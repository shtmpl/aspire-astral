package aspire.astral.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ResponseVacancy {

    private String id;
    private String name;
    private String description;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("published_at")
    private Date publishedAt;
    private ResponseVacancySalary salary;
    private ResponseVacancyEmployment employment;
    private ResponseVacancyEmployer employer;
    private ResponseVacancyContact contacts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public ResponseVacancySalary getSalary() {
        return salary;
    }

    public void setSalary(ResponseVacancySalary salary) {
        this.salary = salary;
    }

    public ResponseVacancyEmployment getEmployment() {
        return employment;
    }

    public void setEmployment(ResponseVacancyEmployment employment) {
        this.employment = employment;
    }

    public ResponseVacancyEmployer getEmployer() {
        return employer;
    }

    public void setEmployer(ResponseVacancyEmployer employer) {
        this.employer = employer;
    }

    public ResponseVacancyContact getContacts() {
        return contacts;
    }

    public void setContacts(ResponseVacancyContact contacts) {
        this.contacts = contacts;
    }
}
