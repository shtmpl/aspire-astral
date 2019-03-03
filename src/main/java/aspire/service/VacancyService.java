package aspire.service;

import aspire.domain.Origin;
import aspire.domain.Vacancy;

import java.util.List;

public interface VacancyService {

    List<Vacancy> findVacancies(Origin origin);

    List<Vacancy> findVacanciesByTitleContaining(Origin origin, String title);

    Vacancy findVacancyById(Origin origin, String id);

    Vacancy createVacancy(Origin origin, Vacancy vacancy);

    Vacancy updateVacancy(Origin origin, String id, Vacancy vacancy);

    Vacancy deleteVacancy(Origin origin, String id);

}
