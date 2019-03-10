package aspire.astral.repository;

import aspire.astral.domain.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalVacancyRepository extends JpaRepository<Vacancy, Long> {

    <X> Page<X> findAllBy(Class<X> projection, Pageable pageable);

    <X> Page<X> findAllByTitleContainingIgnoreCase(Class<X> projection, String title, Pageable pageable);

    Optional<Vacancy> findByIdExternalAndOrigin(String idExternal, String origin);
}
