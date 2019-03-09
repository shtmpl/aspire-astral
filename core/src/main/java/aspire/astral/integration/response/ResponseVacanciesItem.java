package aspire.astral.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ResponseVacanciesItem {

    private String id;
    private String name;
    @JsonProperty("published_at")
    private Date datePublished;
    private ResponseSalary salary;

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

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public ResponseSalary getSalary() {
        return salary;
    }

    public void setSalary(ResponseSalary salary) {
        this.salary = salary;
    }
}
