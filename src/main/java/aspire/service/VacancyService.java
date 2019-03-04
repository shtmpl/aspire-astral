package aspire.service;

import aspire.domain.Origin;
import aspire.domain.Vacancy;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacancyService {

    List<Vacancy> findVacancies(String origin, Pageable pageable);

    List<Vacancy> findVacanciesByTitleLike(String origin, String title, Pageable pageable);

    Vacancy findVacancyById(String origin, String id);

    Vacancy createVacancy(String origin, Vacancy vacancy);

    Vacancy updateVacancy(String origin, String id, Vacancy vacancy);

    Vacancy deleteVacancy(String origin, String id);

}
