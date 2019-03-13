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
                return findLocalVacancyOverviews(pageable);
            case Origin.REMOTE:
                return findRemoteVacancyOverviews(pageable);
            default:
                throw new OriginUndefinedException();
        }
    }

    private Page<VacancyOverview> findLocalVacancyOverviews(Pageable pageable) {
        return localVacancyRepository.findAllBy(VacancyOverview.class, pageable);
    }

    private Page<VacancyOverview> findRemoteVacancyOverviews(Pageable pageable) {
        return remoteVacancyRepository.findAllBy(VacancyOverview.class, pageable);
    }

    @Override
    public Page<VacancyOverview> findVacancyOverviewsByTitleLike(String origin, String title, Pageable pageable) {
        switch (origin) {
            case Origin.LOCAL:
                return findLocalVacancyOverviewsByTitleLike(title, pageable);
            case Origin.REMOTE:
                return findRemoteVacancyOverviewsByTitleLike(title, pageable);
            default:
                throw new OriginUndefinedException();
        }
    }

    private Page<VacancyOverview> findLocalVacancyOverviewsByTitleLike(String title, Pageable pageable) {
        return localVacancyRepository.findAllByTitleContainingIgnoreCase(VacancyOverview.class, title, pageable);
    }

    private Page<VacancyOverview> findRemoteVacancyOverviewsByTitleLike(String title, Pageable pageable) {
        return remoteVacancyRepository.findAllByTitleContainingIgnoreCase(VacancyOverview.class, title, pageable);
    }

    @Override
    public Vacancy findVacancy(String origin, String id) {
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
        return localVacancyRepository.findByIdExposedAndOrigin(id, Origin.LOCAL)
                .orElseThrow(() -> new VacancyNotFoundException(
                        String.format("No vacancy for id: %s is defined in local repository", id)));
    }

    private Vacancy findRemoteVacancyById(String id) {
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
                return acquireRemoteVacancy(id);
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy acquireRemoteVacancy(String id) {
        Vacancy vacancy = findRemoteVacancyById(id);

        return localVacancyRepository.findByIdExposedAndOrigin(id, Origin.REMOTE)
                .map((Vacancy it) -> updateLocalVacancy(it.getIdExposed(), vacancy))
                .orElseGet(() -> createLocalVacancy(vacancy));
    }

    @Override
    public Vacancy createVacancy(String origin, Vacancy vacancy) {
        switch (origin) {
            case Origin.LOCAL:
                return createLocalVacancy(vacancy);
            case Origin.REMOTE:
                throw new OriginUnsupportedOperationException(
                        String.format("Operation: %s is not supported for origin: %s", "create", origin));
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy createLocalVacancy(Vacancy vacancy) {
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
            result.setContacts(contacts);
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
                        String.format("Operation: %s is not supported for origin: %s", "update", origin));
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy updateLocalVacancy(String id, Vacancy vacancy) {
        Vacancy found = localVacancyRepository.findByIdExposedAndOrigin(id, Origin.LOCAL).orElse(null);
        if (found == null) {
            return createLocalVacancy(vacancy); // Satisfy the idempotence
        }

        String idExposed = vacancy.getIdExposed();
        if (idExposed != null && !idExposed.equals(found.getIdExposed())) {
            found.setIdExposed(idExposed);
        }

        String origin = vacancy.getOrigin();
        if (origin != null && !origin.equals(found.getOrigin())) {
            found.setOrigin(origin);
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
            found.setContacts(contacts);
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
                        String.format("Operation: %s is not supported for origin: %s", "delete", origin));
            default:
                throw new OriginUndefinedException();
        }
    }

    private Vacancy deleteLocalVacancy(String id) {
        Vacancy found = localVacancyRepository.findByIdExposedAndOrigin(id, Origin.LOCAL).orElse(null);
        if (found == null) {
            throw new VacancyNotFoundException(String.format("No vacancy found for id: %s", id));
        }

        localVacancyRepository.delete(found);

        return found;
    }
}
