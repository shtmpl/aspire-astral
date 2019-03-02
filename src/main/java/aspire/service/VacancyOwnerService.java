package aspire.service;

import aspire.domain.VacancyOwner;

public interface VacancyOwnerService {

    VacancyOwner findOrCreateOrganizationByName(String name);

    VacancyOwner findOrSaveOrganizationByName(String name);

}
