package aspire.service;

import aspire.domain.Employment;
import aspire.domain.Origin;
import aspire.domain.OriginUndefinedException;
import aspire.domain.OriginUnsupportedOperationException;
import aspire.domain.Vacancy;
import aspire.domain.Employer;
import aspire.domain.VacancyNotFoundException;
import aspire.repository.LocalVacancyRepository;
import aspire.repository.RemoteVacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class VacancyServiceImpl implements VacancyService {

    private final LocalVacancyRepository localVacancyRepository;
    private final RemoteVacancyRepository remoteVacancyRepository;

    private final EmployerService employerService;

    @Autowired
    public VacancyServiceImpl(LocalVacancyRepository localVacancyRepository,
                              RemoteVacancyRepository remoteVacancyRepository,
                              EmployerService employerService) {
        this.localVacancyRepository = localVacancyRepository;
        this.remoteVacancyRepository = remoteVacancyRepository;

        this.employerService = employerService;
    }

    @Override
    public List<Vacancy> findVacancies(String origin, Pageable pageable) {
        switch (origin) {
            case Origin.LOCAL:
                return findLocalVacancies(pageable);
            case Origin.REMOTE:
                return findRemoteVacancies(pageable);
            default:
                throw new OriginUndefinedException();
        }
    }

    private List<Vacancy> findLocalVacancies(Pageable pageable) {
        return localVacancyRepository.findAll(pageable).getContent();
    }

    private List<Vacancy> findRemoteVacancies(Pageable pageable) {
        return remoteVacancyRepository.findAll(pageable);
    }

    @Override
    public List<Vacancy> findVacanciesByTitleLike(String origin, String title, Pageable pageable) {
        switch (origin) {
            case Origin.LOCAL:
                return findLocalVacanciesByTitleContaining(title, pageable);
            case Origin.REMOTE:
                return findRemoteVacanciesByTitleContaining(title, pageable);
            default:
                throw new OriginUndefinedException();
        }
    }

    private List<Vacancy> findLocalVacanciesByTitleContaining(String title, Pageable pageable) {
        return localVacancyRepository.findAllByTitleContaining(title, pageable);
    }

    private List<Vacancy> findRemoteVacanciesByTitleContaining(String title, Pageable pageable) {
        return remoteVacancyRepository.findAllByTitleContaining(title, pageable);
    }

    @Override
    public Vacancy findVacancyById(String origin, String id) {
        switch (origin) {
            case Origin.LOCAL:
                return findLocalVacancyById(id);
            case Origin.REMOTE:
                return findRemoteVacancyById(id);
            default:
                throw new OriginUndefinedException();
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
    public Vacancy createVacancy(String origin, Vacancy vacancy) {
        switch (origin) {
            case Origin.LOCAL:
                return createLocalVacancy(vacancy);
            case Origin.REMOTE:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s not supported for origin: %s", "create", origin));
            default:
                throw new OriginUndefinedException();
        }
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
    public Vacancy updateVacancy(String origin, String id, Vacancy vacancy) {
        switch (origin) {
            case Origin.LOCAL:
                return updateLocalVacancy(id, vacancy);
            case Origin.REMOTE:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s not supported for origin: %s", "update", origin));
            default:
                throw new OriginUndefinedException();
        }
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

        // FIXME:
//        BigDecimal oldSalaryFrom = found.getSalaryFrom();
//        BigDecimal newSalaryFrom = vacancy.getSalaryFrom();
//        if (newSalaryFrom != null && !newSalaryFrom.equals(oldSalaryFrom)) {
//            found.setSalaryFrom(newSalaryFrom);
//        }
//
//        BigDecimal oldSalaryTo = found.getSalaryTo();
//        BigDecimal newSalaryTo = vacancy.getSalaryTo();
//        if (newSalaryTo != null && !newSalaryTo.equals(oldSalaryTo)) {
//            found.setSalaryTo(newSalaryTo);
//        }

        Employment oldEmployment = found.getEmployment();
        Employment newEmployment = vacancy.getEmployment();
        if (newEmployment != null && !newEmployment.equals(oldEmployment)) {
            found.setEmployment(newEmployment);
        }

        return localVacancyRepository.save(found);
    }

    @Override
    public Vacancy deleteVacancy(String origin, String id) {
        switch (origin) {
            case Origin.LOCAL:
                return deleteLocalVacancy(id);
            case Origin.REMOTE:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s not supported for origin: %s", "delete", origin));
            default:
                throw new OriginUndefinedException();
        }
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
