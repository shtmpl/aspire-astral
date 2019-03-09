package aspire.astral.repository;

import aspire.astral.domain.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RemoteVacancyRepository {

    Page<Vacancy> findAll(Pageable pageable);

    List<Vacancy> findAllByTitleContaining(String title, Pageable pageable);

    Optional<Vacancy> findById(String id);
}
