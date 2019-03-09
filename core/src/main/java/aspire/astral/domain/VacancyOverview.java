package aspire.astral.domain;

import java.util.Date;

public interface VacancyOverview {

    Long getId();

    String getIdExternal();

    String getOrigin();

    Date getDatePublished();

    String getTitle();

    Salary getSalary();
}
