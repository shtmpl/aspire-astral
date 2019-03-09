package aspire.astral.service;

import aspire.astral.domain.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacancyService {

    Page<Vacancy> findVacancies(String origin, Pageable pageable);

    List<Vacancy> findVacanciesByTitleLike(String origin, String title, Pageable pageable);

    Vacancy findVacancyById(String origin, String id);

    Vacancy createVacancy(String origin, Vacancy vacancy);

    Vacancy updateVacancy(String origin, String id, Vacancy vacancy);

    Vacancy deleteVacancy(String origin, String id);
}
