package aspire.repository;

import aspire.domain.VacancyOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalVacancyOwnerRepository extends JpaRepository<VacancyOwner, Long> {

    Optional<VacancyOwner> findByName(String name);

}
