package aspire.astral.repository;

import aspire.astral.domain.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalVacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findAllByTitleContaining(String title, Pageable pageable);
}
