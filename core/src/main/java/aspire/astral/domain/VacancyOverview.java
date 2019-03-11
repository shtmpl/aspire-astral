package aspire.astral.domain;

import java.util.Date;

public interface VacancyOverview {

    Long getId();

    String getIdExposed();

    String getOrigin();

    Date getDatePublished();

    String getTitle();

    Salary getSalary();
}
