package aspire.service;

import aspire.controller.request.InputVacancy;
import aspire.domain.Vacancy;

import java.util.List;

public interface VacancyService {

    Vacancy getVacancy(Long id);

    List<Vacancy> findVacancies();

    List<Vacancy> findVacanciesByTitle(String title);

    List<Vacancy> findVacanciesByTitleLike(String title);

    Vacancy createVacancy(InputVacancy request);

    Vacancy updateVacancy(Long id, InputVacancy request);

    Vacancy deleteVacancy(Long id);

}
