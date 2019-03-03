package aspire.repository;

import aspire.domain.Vacancy;

import java.util.List;
import java.util.Optional;

public interface RemoteVacancyRepository {

    List<Vacancy> findAll();

    List<Vacancy> findAllByTitleContaining(String title);

    Optional<Vacancy> findById(String id);

}
