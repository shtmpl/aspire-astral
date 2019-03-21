package aspire.astral.controller.response;

import java.util.Date;

public class ResponseVacancyOverview {

    private String id;
    private String origin;
    private Date datePublished;
    private String title;
    private ResponseSalary salary;
    private ResponseEmployer employer;

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

    public ResponseSalary getSalary() {
        return salary;
    }

    public void setSalary(ResponseSalary salary) {
        this.salary = salary;
    }

    public ResponseEmployer getEmployer() {
        return employer;
    }

    public void setEmployer(ResponseEmployer employer) {
        this.employer = employer;
    }
}
