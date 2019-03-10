package aspire.astral.service;

import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacancyService {

    Page<VacancyOverview> findVacancyOverviews(String origin, Pageable pageable);

    Page<VacancyOverview> findVacancyOverviewsByTitleLike(String origin, String title, Pageable pageable);

    Vacancy findVacancy(String origin, String id);

    Vacancy acquireVacancy(String origin, String id);

    Vacancy createVacancy(String origin, Vacancy vacancy);

    Vacancy updateVacancy(String origin, String id, Vacancy vacancy);

    Vacancy deleteVacancy(String origin, String id);
}
