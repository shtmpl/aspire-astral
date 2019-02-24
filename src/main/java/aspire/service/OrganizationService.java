package aspire.service;

import aspire.domain.Organization;

public interface OrganizationService {

    Organization findOrCreateOrganizationByName(String name);

    Organization findOrSaveOrganizationByName(String name);

}
