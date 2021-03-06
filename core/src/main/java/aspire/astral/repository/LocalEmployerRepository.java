package aspire.astral.repository;

import aspire.astral.domain.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalEmployerRepository extends JpaRepository<Employer, Long> {

    Optional<Employer> findByName(String name);
}
