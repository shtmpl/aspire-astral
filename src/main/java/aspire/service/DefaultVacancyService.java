package aspire.service;

import aspire.controller.request.InputOrganization;
import aspire.controller.request.InputVacancy;
import aspire.domain.Employment;
import aspire.domain.VacancyOwner;
import aspire.domain.Vacancy;
import aspire.repository.LocalVacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultVacancyService implements VacancyService {

    private final LocalVacancyRepository localVacancyRepository;

    private final VacancyOwnerService vacancyOwnerService;

    @Autowired
    public DefaultVacancyService(LocalVacancyRepository localVacancyRepository,
                                 VacancyOwnerService vacancyOwnerService) {
        this.localVacancyRepository = localVacancyRepository;

        this.vacancyOwnerService = vacancyOwnerService;
    }

    @Override
    public Vacancy getVacancy(Long id) {
        return localVacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException(String.format("No vacancy found for id: %d", id)));
    }

    @Override
    public List<Vacancy> findVacancies() {
        return localVacancyRepository.findAll();
    }

    @Override
    public List<Vacancy> findVacanciesByTitle(String title) {
        return localVacancyRepository.findAllByTitle(title);
    }

    @Override
    public List<Vacancy> findVacanciesByTitleLike(String title) {
        return localVacancyRepository.findAllByTitleLike(title);
    }

    @Override
    public Vacancy createVacancy(InputVacancy input) {
        Vacancy vacancy = new Vacancy();
        if (input.getTitle() != null) {
            vacancy.setTitle(input.getTitle());
        }

        if (input.getDescription() != null) {
            vacancy.setDescription(input.getDescription());
        }

        if (input.getSalary() != null) {
            vacancy.setSalary(input.getSalary());
        }

        Employment employment = Employment.fromString(input.getEmployment());
        if (employment != null) {
            vacancy.setEmployment(employment);
        }

        InputOrganization inputOrganization = input.getInputOrganization();
        if (inputOrganization != null) {
            VacancyOwner vacancyOwner = vacancyOwnerService
                    .findOrSaveOrganizationByName(inputOrganization.getName());

            vacancy.setOwner(vacancyOwner);
        }

        return localVacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy updateVacancy(Long id, InputVacancy input) {
        return localVacancyRepository.findById(id).map((Vacancy found) -> {
            String oldTitle = found.getTitle();
            String newTitle = input.getTitle();
            if (newTitle != null && !newTitle.equals(oldTitle)) {
                found.setTitle(newTitle);
            }

            String oldDescription = found.getDescription();
            String newDescription = input.getDescription();
            if (newDescription != null && !newDescription.equals(oldDescription)) {
                found.setDescription(newDescription);
            }

            BigDecimal oldSalary = found.getSalary();
            BigDecimal newSalary = input.getSalary();
            if (newSalary != null && !newSalary.equals(oldSalary)) {
                found.setSalary(newSalary);
            }

            Employment oldEmployment = found.getEmployment();
            Employment newEmployment = Employment.fromString(input.getEmployment());
            if (newEmployment != null && !newEmployment.equals(oldEmployment)) {
                found.setEmployment(newEmployment);
            }

            return localVacancyRepository.save(found);
        }).orElseGet(() -> createVacancy(input));
    }

    @Override
    public Vacancy deleteVacancy(Long id) {
        return localVacancyRepository.findById(id).map((Vacancy found) -> {
            localVacancyRepository.delete(found);

            return found;
        }).orElseThrow(() -> new VacancyNotFoundException(String.format("No vacancy found for id: %d", id)));
    }

}
