package aspire.astral.service;

import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacancyService {

    Page<VacancyOverview> findVacancyOverviews(String repository, Pageable pageable);

    Page<VacancyOverview> findVacancyOverviewsByTitleLike(String repository, String title, Pageable pageable);

    Vacancy findVacancy(String repository, String id, String origin);

    Vacancy acquireVacancy(String repository, String id, String origin);

    Vacancy createVacancy(String repository, Vacancy vacancy);

    Vacancy updateVacancy(String repository, String id, String origin, Vacancy vacancy);

    Vacancy deleteVacancy(String repository, String id, String origin);
}
