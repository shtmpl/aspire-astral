package aspire.repository;

import aspire.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findAllByTitle(String title);

}
