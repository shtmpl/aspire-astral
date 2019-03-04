package aspire.repository;

import aspire.domain.Vacancy;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RemoteVacancyRepository {

    List<Vacancy> findAll(Pageable pageable);

    List<Vacancy> findAllByTitleContaining(String title);

    Optional<Vacancy> findById(String id);

}
