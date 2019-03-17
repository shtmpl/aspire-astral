package aspire.astral.service;

import aspire.astral.domain.Employer;
import aspire.astral.domain.Employment;
import aspire.astral.domain.Origin;
import aspire.astral.domain.OriginUndefinedException;
import aspire.astral.domain.OriginUnsupportedOperationException;
import aspire.astral.domain.Salary;
import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyContact;
import aspire.astral.domain.VacancyNotFoundException;
import aspire.astral.domain.VacancyOverview;
import aspire.astral.repository.LocalVacancyRepository;
import aspire.astral.repository.RemoteVacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

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
    public Page<VacancyOverview> findVacancyOverviews(String origin, Pageable pageable) {
        switch (origin) {
            case Origin.LOCAL:
                return findVacancyOverviewsInLocalRepository(pageable);
            case Origin.REMOTE:
                return findVacancyOverviewsInRemoteRepository(pageable);
            default:
                throw new OriginUndefinedException();
        }
    }

    private Page<VacancyOverview> findVacancyOverviewsInLocalRepository(Pageable pageable) {
        return localVacancyRepository.findAllBy(VacancyOverview.class, pageable);
    }

    private Page<VacancyOverview> findVacancyOverviewsInRemoteRepository(Pageable pageable) {
        return remoteVacancyRepository.findAllBy(VacancyOverview.class, pageable);
    }

    @Override
    public Page<VacancyOverview> findVacancyOverviewsByTitleLike(String origin, String title, Pageable pageable) {
        switch (origin) {
            case Origin.LOCAL:
                return findVacancyOverviewsInLocalRepositoryByTitleLike(title, pageable);
            case Origin.REMOTE:
                return findVacancyOverviewsInRemoteRepositoryByTitleLike(title, pageable);
            default:
                throw new OriginUndefinedException();
        }
    }

    private Page<VacancyOverview> findVacancyOverviewsInLocalRepositoryByTitleLike(String title, Pageable pageable) {
        return localVacancyRepository.findAllByTitleContainingIgnoreCase(VacancyOverview.class, title, pageable);
    }

    private Page<VacancyOverview> findVacancyOverviewsInRemoteRepositoryByTitleLike(String title, Pageable pageable) {
        return remoteVacancyRepository.findAllByTitleContainingIgnoreCase(VacancyOverview.class, title, pageable);
    }

    @Override
    public Vacancy findVacancy(String origin, String id) {
        switch (origin) {
            case Origin.LOCAL:
                return findVacancyInLocalRepositoryById(id);
            case Origin.REMOTE:
                return findVacancyInRemoteRepositoryById(id);
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy findVacancyInLocalRepositoryById(String id) {
        return localVacancyRepository.findByIdExposedAndOrigin(id, Origin.LOCAL)
                .orElseThrow(() -> new VacancyNotFoundException(
                        String.format("No vacancy for id: %s is defined in local repository", id)));
    }

    private Vacancy findVacancyInRemoteRepositoryById(String id) {
        return remoteVacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException(
                        String.format("No vacancy for id: %s is defined in remote repository", id)));
    }

    @Override
    public Vacancy acquireVacancy(String origin, String id) {
        switch (origin) {
            case Origin.LOCAL:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s is not supported for origin: %s", "acquire", origin));
            case Origin.REMOTE:
                return acquireVacancyFromRemoteRepositoryById(id);
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy acquireVacancyFromRemoteRepositoryById(String id) {
        Vacancy vacancy = findVacancyInRemoteRepositoryById(id);

        return updateVacancyInLocalRepository(id, vacancy);
    }

    @Override
    public Vacancy createVacancy(String origin, Vacancy vacancy) {
        switch (origin) {
            case Origin.LOCAL:
                return createVacancyInLocalRepository(vacancy);
            case Origin.REMOTE:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s is not supported for origin: %s", "create", origin));
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy createVacancyInLocalRepository(Vacancy vacancy) {
        Vacancy result = new Vacancy();

        String idExposed = vacancy.getIdExposed();
        if (idExposed != null) {
            result.setIdExposed(idExposed);
        }

        String origin = vacancy.getOrigin();
        if (origin != null) {
            result.setOrigin(origin);
        }

        Date dateCreated = vacancy.getDateCreated();
        if (dateCreated != null) {
            result.setDateCreated(dateCreated);
        } else {
            result.setDateCreated(Date.from(Instant.now()));
        }

        Date datePublished = vacancy.getDatePublished();
        if (datePublished != null) {
            result.setDatePublished(datePublished);
        }

        String title = vacancy.getTitle();
        if (title != null) {
            result.setTitle(title);
        }

        String description = vacancy.getDescription();
        if (description != null) {
            result.setDescription(description);
        }

        Salary salary = vacancy.getSalary();
        if (salary != null) {
            result.setSalary(salary);
        }

        Employment employment = vacancy.getEmployment();
        if (employment != null) {
            result.setEmployment(employment);
        }

        Employer employer = vacancy.getEmployer();
        if (employer != null) {
            result.setEmployer(employerService.findOrSaveEmployer(employer));
        }

        Set<VacancyContact> contacts = vacancy.getContacts();
        if (contacts != null && !contacts.isEmpty()) {
            result.clearContacts();
            result.addContacts(contacts);
        }

        return localVacancyRepository.save(result);
    }

    @Override
    public Vacancy updateVacancy(String origin, String id, Vacancy vacancy) {
        switch (origin) {
            case Origin.LOCAL:
                return updateVacancyInLocalRepository(id, vacancy);
            case Origin.REMOTE:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s is not supported for origin: %s", "update", origin));
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy updateVacancyInLocalRepository(String id, Vacancy vacancy) {
        Vacancy found = localVacancyRepository.findByIdExposedAndOrigin(id, vacancy.getOrigin()).orElse(null);
        if (found == null) {
            return createVacancyInLocalRepository(vacancy); // Satisfy the idempotence
        }

        String idExposed = vacancy.getIdExposed();
        if (idExposed != null && !idExposed.equals(found.getIdExposed())) {
            found.setIdExposed(idExposed);
        }

        Date dateCreated = vacancy.getDateCreated();
        if (dateCreated != null && !dateCreated.equals(found.getDateCreated())) {
            found.setDateCreated(dateCreated);
        }

        Date datePublished = vacancy.getDatePublished();
        if (datePublished != null && !datePublished.equals(found.getDatePublished())) {
            found.setDatePublished(datePublished);
        }

        String title = vacancy.getTitle();
        if (title != null && !title.equals(found.getTitle())) {
            found.setTitle(title);
        }

        String description = vacancy.getDescription();
        if (description != null && !description.equals(found.getDescription())) {
            found.setDescription(description);
        }

        Salary salary = vacancy.getSalary();
        if (salary != null && !salary.equals(found.getSalary())) {
            found.setSalary(salary);
        }

        Employment employment = vacancy.getEmployment();
        if (employment != null && !employment.equals(found.getEmployment())) {
            found.setEmployment(employment);
        }

        Employer employer = vacancy.getEmployer();
        if (employer != null) {
            found.setEmployer(employerService.findOrSaveEmployer(employer));
        }

        Set<VacancyContact> contacts = vacancy.getContacts();
        if (contacts != null && !contacts.isEmpty()) {
            found.clearContacts();
            found.addContacts(contacts);
        }

        return localVacancyRepository.save(found);
    }

    @Override
    public Vacancy deleteVacancy(String origin, String id) {
        switch (origin) {
            case Origin.LOCAL:
                return deleteVacancyInLocalRepositoryById(origin, id);
            case Origin.REMOTE:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s is not supported for origin: %s", "delete", origin));
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy deleteVacancyInLocalRepositoryById(String origin, String id) {
        Vacancy found = localVacancyRepository.findByIdExposedAndOrigin(id, origin).orElse(null);
        if (found == null) {
            throw new VacancyNotFoundException(String.format("No vacancy found for id: %s", id));
        }

        localVacancyRepository.delete(found);

        return found;
    }
}
