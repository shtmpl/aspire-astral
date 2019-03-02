package aspire.service;

import aspire.domain.Source;
import aspire.domain.Vacancy;

import java.util.List;

public interface VacancyService {

    List<Vacancy> findVacancies(Source source);

    List<Vacancy> findVacanciesByTitle(Source source, String title);

    List<Vacancy> findVacanciesByTitleContaining(Source source, String title);

    Vacancy findVacancyById(Source source, Long id);

    Vacancy createVacancy(Source source, Vacancy vacancy);

    Vacancy updateVacancy(Source source, Long id, Vacancy vacancy);

    Vacancy deleteVacancy(Source source, Long id);

}
