package aspire.repository;

import aspire.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalVacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findAllByTitle(String title);

}
