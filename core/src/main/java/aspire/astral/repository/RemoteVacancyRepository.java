package aspire.astral.repository;

import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RemoteVacancyRepository {

    Page<VacancyOverview> findAllBy(Class<VacancyOverview> projection, Pageable pageable);

    Page<VacancyOverview> findAllByTitleContainingIgnoreCase(Class<VacancyOverview> projection, String title, Pageable pageable);

    Optional<Vacancy> findById(String id);
}
