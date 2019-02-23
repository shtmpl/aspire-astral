package aspire.service;

import aspire.controller.command.IssueRequestCommand;
import aspire.controller.command.ReadRequestCommand;
import aspire.domain.Vacancy;

import java.util.List;

public interface VacancyService {

    Vacancy getVacancy(Long id);

    List<Vacancy> findVacancies();

    List<Vacancy> findVacanciesByTitle(String title);

}
