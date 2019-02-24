package aspire.service;

import aspire.domain.Organization;
import aspire.repository.LocalOrganizationRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrganizationService implements OrganizationService {

    private final LocalOrganizationRepository localOrganizationRepository;

    public DefaultOrganizationService(LocalOrganizationRepository localOrganizationRepository) {
        this.localOrganizationRepository = localOrganizationRepository;
    }

    @Override
    public Organization findOrCreateOrganizationByName(String name) {
        return localOrganizationRepository.findByName(name).orElseGet(Organization::new);
    }

    @Override
    public Organization findOrSaveOrganizationByName(String name) {
        return localOrganizationRepository.findByName(name).orElseGet(() -> {
            Organization organization = new Organization();
            organization.setName(name);

            return localOrganizationRepository.save(organization);
        });
    }
}
