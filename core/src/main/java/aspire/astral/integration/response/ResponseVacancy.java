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
    private ResponseSalary salary;
    private ResponseEmployment employment;
    private ResponseEmployer employer;
    private ResponseContact contacts;

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

    public ResponseSalary getSalary() {
        return salary;
    }

    public void setSalary(ResponseSalary salary) {
        this.salary = salary;
    }

    public ResponseEmployment getEmployment() {
        return employment;
    }

    public void setEmployment(ResponseEmployment employment) {
        this.employment = employment;
    }

    public ResponseEmployer getEmployer() {
        return employer;
    }

    public void setEmployer(ResponseEmployer employer) {
        this.employer = employer;
    }

    public ResponseContact getContacts() {
        return contacts;
    }

    public void setContacts(ResponseContact contacts) {
        this.contacts = contacts;
    }
}
