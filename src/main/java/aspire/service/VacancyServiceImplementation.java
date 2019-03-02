package aspire.service;

import aspire.domain.Employment;
import aspire.domain.Source;
import aspire.domain.Vacancy;
import aspire.domain.Employer;
import aspire.repository.LocalVacancyRepository;
import aspire.repository.RemoteVacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class VacancyServiceImplementation implements VacancyService {

    private final LocalVacancyRepository localVacancyRepository;
    private final RemoteVacancyRepository remoteVacancyRepository;

    private final EmployerService employerService;

    @Autowired
    public VacancyServiceImplementation(LocalVacancyRepository localVacancyRepository,
                                        RemoteVacancyRepository remoteVacancyRepository,
                                        EmployerService employerService) {
        this.localVacancyRepository = localVacancyRepository;
        this.remoteVacancyRepository = remoteVacancyRepository;

        this.employerService = employerService;
    }

    @Override
    public List<Vacancy> findVacancies(Source source) {
        return localVacancyRepository.findAll();
    }

    @Override
    public List<Vacancy> findVacanciesByTitle(Source source, String title) {
        return localVacancyRepository.findAllByTitle(title);
    }

    @Override
    public List<Vacancy> findVacanciesByTitleContaining(Source source, String title) {
        return localVacancyRepository.findAllByTitleContaining(title);
    }

    @Override
    public Vacancy findVacancyById(Source source, Long id) {
        return localVacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException(String.format("No vacancy found for id: %d", id)));
    }

    @Override
    public Vacancy createVacancy(Source source, Vacancy vacancy) {
        Vacancy result = new Vacancy();

        vacancy.setDateCreated(Date.from(Instant.now()));

        if (vacancy.getTitle() != null) {
            result.setTitle(vacancy.getTitle());
        }

        if (vacancy.getDescription() != null) {
            result.setDescription(vacancy.getDescription());
        }

        if (vacancy.getSalary() != null) {
            result.setSalary(vacancy.getSalary());
        }

        Employment employment = vacancy.getEmployment();
        if (employment != null) {
            result.setEmployment(employment);
        }

        Employer owner = vacancy.getEmployer();
        if (owner != null) {
            employerService.findOrSaveEmployer(owner);

            result.setEmployer(owner);
        }

        return localVacancyRepository.save(result);
    }

    @Override
    public Vacancy updateVacancy(Source source, Long id, Vacancy vacancy) {
        return localVacancyRepository.findById(id).map((Vacancy found) -> {
            String oldTitle = found.getTitle();
            String newTitle = vacancy.getTitle();
            if (newTitle != null && !newTitle.equals(oldTitle)) {
                found.setTitle(newTitle);
            }

            String oldDescription = found.getDescription();
            String newDescription = vacancy.getDescription();
            if (newDescription != null && !newDescription.equals(oldDescription)) {
                found.setDescription(newDescription);
            }

            BigDecimal oldSalary = found.getSalary();
            BigDecimal newSalary = vacancy.getSalary();
            if (newSalary != null && !newSalary.equals(oldSalary)) {
                found.setSalary(newSalary);
            }

            Employment oldEmployment = found.getEmployment();
            Employment newEmployment = vacancy.getEmployment();
            if (newEmployment != null && !newEmployment.equals(oldEmployment)) {
                found.setEmployment(newEmployment);
            }

            return localVacancyRepository.save(found);
        }).orElseGet(() -> createVacancy(source, vacancy));
    }

    @Override
    public Vacancy deleteVacancy(Source source, Long id) {
        return localVacancyRepository.findById(id).map((Vacancy found) -> {
            localVacancyRepository.delete(found);

            return found;
        }).orElseThrow(() -> new VacancyNotFoundException(String.format("No vacancy found for id: %d", id)));
    }

}
