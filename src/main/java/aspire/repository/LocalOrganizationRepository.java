package aspire.repository;

import aspire.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalOrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByName(String name);

}
