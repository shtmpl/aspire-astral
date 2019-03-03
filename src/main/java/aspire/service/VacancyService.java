package aspire.service;

import aspire.domain.Origin;
import aspire.domain.Vacancy;

import java.util.List;

public interface VacancyService {

    List<Vacancy> findVacancies(String origin);

    List<Vacancy> findVacanciesByTitleContaining(String origin, String title);

    Vacancy findVacancyById(String origin, String id);

    Vacancy createVacancy(String origin, Vacancy vacancy);

    Vacancy updateVacancy(String origin, String id, Vacancy vacancy);

    Vacancy deleteVacancy(String origin, String id);

}
