package aspire.service;

import aspire.domain.Employment;
import aspire.domain.Origin;
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
import java.util.Optional;

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
    public List<Vacancy> findVacancies(Origin origin) {
        switch (origin) {
            case LOCAL:
                return findLocalVacancies();
            case REMOTE:
                return findRemoteVacancies();
            default:
                throw new OriginUnknownException(String.format("Unknown origin: %s", origin));
        }
    }

    private List<Vacancy> findLocalVacancies() {
        return localVacancyRepository.findAll();
    }

    private List<Vacancy> findRemoteVacancies() {
        return remoteVacancyRepository.findAll();
    }

    @Override
    public List<Vacancy> findVacanciesByTitleContaining(Origin origin, String title) {
        switch (origin) {
            case LOCAL:
                return findLocalVacanciesByTitleContaining(title);
            case REMOTE:
                return findRemoteVacanciesByTitleContaining(title);
            default:
                throw new OriginUnknownException(String.format("Unknown origin: %s", origin));
        }
    }

    private List<Vacancy> findLocalVacanciesByTitleContaining(String title) {
        return localVacancyRepository.findAllByTitleContaining(title);
    }

    private List<Vacancy> findRemoteVacanciesByTitleContaining(String title) {
        return remoteVacancyRepository.findAllByTitleContaining(title);
    }

    @Override
    public Vacancy findVacancyById(Origin origin, String id) {
        switch (origin) {
            case LOCAL:
                return findLocalVacancyById(id);
            case REMOTE:
                return findRemoteVacancyById(id);
            default:
                throw new OriginUnknownException(String.format("Unknown origin: %s", origin));
        }
    }

    private Vacancy findLocalVacancyById(String id) {
        return localVacancyRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new VacancyNotFoundException(
                        String.format("No vacancy for id: %s is defined in local repository", id)));

    }

    private Vacancy findRemoteVacancyById(String id) {
        return remoteVacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException(
                        String.format("No vacancy for id: %s is defined in remote repository", id)));

    }

    @Override
    public Vacancy createVacancy(Origin origin, Vacancy vacancy) {
        if (origin == Origin.LOCAL) {
            return createLocalVacancy(vacancy);
        }

        throw new OriginUnsupportedOperationException(
                String.format("Operation: %s not supported for origin: %s", "create", origin));
    }

    private Vacancy createLocalVacancy(Vacancy vacancy) {
        Vacancy result = new Vacancy();

        vacancy.setDateCreated(Date.from(Instant.now()));

        if (vacancy.getTitle() != null) {
            result.setTitle(vacancy.getTitle());
        }

        if (vacancy.getDescription() != null) {
            result.setDescription(vacancy.getDescription());
        }

        // FIXME:
//        if (vacancy.getSalary() != null) {
//            result.setSalary(vacancy.getSalary());
//        }

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
    public Vacancy updateVacancy(Origin origin, String id, Vacancy vacancy) {
        if (origin == Origin.LOCAL) {
            return updateLocalVacancy(id, vacancy);
        }

        throw new OriginUnsupportedOperationException(
                String.format("Operation: %s not supported for origin: %s", "update", origin));
    }

    private Vacancy updateLocalVacancy(String id, Vacancy vacancy) {
        Vacancy found = localVacancyRepository.findById(Long.valueOf(id)).orElse(null);
        if (found == null) {
            return createLocalVacancy(vacancy);
        }

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

        BigDecimal oldSalaryFrom = found.getSalaryFrom();
        BigDecimal newSalaryFrom = vacancy.getSalaryFrom();
        if (newSalaryFrom != null && !newSalaryFrom.equals(oldSalaryFrom)) {
            found.setSalaryFrom(newSalaryFrom);
        }

        BigDecimal oldSalaryTo = found.getSalaryTo();
        BigDecimal newSalaryTo = vacancy.getSalaryTo();
        if (newSalaryTo != null && !newSalaryTo.equals(oldSalaryTo)) {
            found.setSalaryTo(newSalaryTo);
        }

        Employment oldEmployment = found.getEmployment();
        Employment newEmployment = vacancy.getEmployment();
        if (newEmployment != null && !newEmployment.equals(oldEmployment)) {
            found.setEmployment(newEmployment);
        }

        return localVacancyRepository.save(found);
    }

    @Override
    public Vacancy deleteVacancy(Origin origin, String id) {
        if (origin == Origin.LOCAL) {
            return deleteLocalVacancy(id);
        }

        throw new OriginUnsupportedOperationException(
                String.format("Operation: %s not supported for origin: %s", "delete", origin));
    }

    private Vacancy deleteLocalVacancy(String id) {
        Vacancy found = localVacancyRepository.findById(Long.valueOf(id)).orElse(null);
        if (found == null) {
            throw new VacancyNotFoundException(String.format("No vacancy found for id: %s", id));
        }

        localVacancyRepository.delete(found);

        return found;
    }

}
