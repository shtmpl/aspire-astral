package aspire.astral.repository;

import aspire.astral.domain.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalVacancyRepository extends JpaRepository<Vacancy, Long> {

    Page<Vacancy> findAllByTitleContaining(String title, Pageable pageable);
}
