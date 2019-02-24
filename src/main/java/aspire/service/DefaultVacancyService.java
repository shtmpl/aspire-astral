package aspire.service;

import aspire.controller.request.InputOrganization;
import aspire.controller.request.InputVacancy;
import aspire.domain.Organization;
import aspire.domain.Vacancy;
import aspire.repository.LocalVacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultVacancyService implements VacancyService {

    private final LocalVacancyRepository localVacancyRepository;

    private final OrganizationService organizationService;

    @Autowired
    public DefaultVacancyService(LocalVacancyRepository localVacancyRepository,
                                 OrganizationService organizationService) {
        this.localVacancyRepository = localVacancyRepository;

        this.organizationService = organizationService;
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

        Vacancy.EmploymentType employmentType = Vacancy.EmploymentType.fromString(input.getEmploymentType());
        if (employmentType != null) {
            vacancy.setEmploymentType(employmentType);
        }

        InputOrganization inputOrganization = input.getInputOrganization();
        if (inputOrganization != null) {
            Organization organization = organizationService
                    .findOrSaveOrganizationByName(inputOrganization.getName());

            vacancy.setOrganization(organization);
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

            Vacancy.EmploymentType oldEmploymentType = found.getEmploymentType();
            Vacancy.EmploymentType newEmploymentType = Vacancy.EmploymentType.fromString(input.getEmploymentType());
            if (newEmploymentType != null && !newEmploymentType.equals(oldEmploymentType)) {
                found.setEmploymentType(newEmploymentType);
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
