package aspire.astral.service;

import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service for {@link Vacancy} and {@link VacancyOverview} related operations.
 */
public interface VacancyService {

    /**
     * Returns a {@link Page} of vacancies found in the specified repository.
     * Pagination parameters are determined by the supplied {@link Pageable} argument.
     */
    Page<VacancyOverview> findVacancyOverviews(String repository, Pageable pageable);

    /**
     * Returns a {@link Page} of vacancies found in the specified repository with title containing the specified string.
     * Pagination parameters are determined by the supplied {@link Pageable} argument.
     */
    Page<VacancyOverview> findVacancyOverviewsByTitleLike(String repository, String title, Pageable pageable);

    /**
     * Returns a {@link Vacancy} with the specified {@code id} and {@code origin} found in the specified repository.
     */
    Vacancy findVacancy(String repository, String id, String origin);

    /**
     * Pulls (imports & saves) a {@link Vacancy} with the specified {@code id} and {@code origin}
     * from the specified repository into the local repository.
     */
    Vacancy pullVacancy(String repository, String id, String origin);

    /**
     * Creates a new {@link Vacancy} in the specified repository.
     *
     * @param repository repository the {@link Vacancy} exists in
     * @param vacancy    vacancy parameters
     * @return created {@link Vacancy}
     */
    Vacancy createVacancy(String repository, Vacancy vacancy);

    /**
     * Updates an existing {@link Vacancy} in the specified repository.
     */
    Vacancy updateVacancy(String repository, String id, String origin, Vacancy vacancy);

    /**
     * Deletes an existing {@link Vacancy} from the specified repository.
     */
    Vacancy deleteVacancy(String repository, String id, String origin);
}
