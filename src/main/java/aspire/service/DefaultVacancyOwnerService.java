package aspire.service;

import aspire.domain.VacancyOwner;
import aspire.repository.LocalVacancyOwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultVacancyOwnerService implements VacancyOwnerService {

    private final LocalVacancyOwnerRepository localVacancyOwnerRepository;

    public DefaultVacancyOwnerService(LocalVacancyOwnerRepository localVacancyOwnerRepository) {
        this.localVacancyOwnerRepository = localVacancyOwnerRepository;
    }

    @Override
    public VacancyOwner findOrCreateOrganizationByName(String name) {
        return localVacancyOwnerRepository.findByName(name).orElseGet(VacancyOwner::new);
    }

    @Override
    public VacancyOwner findOrSaveOrganizationByName(String name) {
        return localVacancyOwnerRepository.findByName(name).orElseGet(() -> {
            VacancyOwner vacancyOwner = new VacancyOwner();
            vacancyOwner.setName(name);

            return localVacancyOwnerRepository.save(vacancyOwner);
        });
    }
}
