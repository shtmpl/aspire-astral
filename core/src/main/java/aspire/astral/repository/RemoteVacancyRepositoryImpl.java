package aspire.astral.repository;

import aspire.astral.domain.Employer;
import aspire.astral.domain.Employment;
import aspire.astral.domain.Origin;
import aspire.astral.domain.Salary;
import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyContact;
import aspire.astral.domain.VacancyOverview;
import aspire.astral.integration.HeadHunterClient;
import aspire.astral.integration.response.ResponseVacancies;
import aspire.astral.integration.response.ResponseVacanciesItem;
import aspire.astral.integration.response.ResponseVacancy;
import aspire.astral.integration.response.ResponseVacancyContact;
import aspire.astral.integration.response.ResponseVacancyContactPhone;
import aspire.astral.integration.response.ResponseVacancyEmployer;
import aspire.astral.integration.response.ResponseEmployment;
import aspire.astral.integration.response.ResponseSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class RemoteVacancyRepositoryImpl implements RemoteVacancyRepository {

    private final HeadHunterClient headHunterClient;

    @Autowired
    public RemoteVacancyRepositoryImpl(HeadHunterClient headHunterClient) {
        this.headHunterClient = headHunterClient;
    }

    @Override
    public Page<VacancyOverview> findAllBy(Class<VacancyOverview> projection, Pageable pageable) {
        return headHunterClient.getVacancies(pageable)
                .map((ResponseVacancies it) -> extractVacancyOverviewsFromResponse(it, pageable))
                .orElse(Page.empty(pageable));
    }

    @Override
    public Page<VacancyOverview> findAllByTitleContainingIgnoreCase(Class<VacancyOverview> projection, String title, Pageable pageable) {
        return headHunterClient.getVacancies(title, pageable)
                .map((ResponseVacancies it) -> extractVacancyOverviewsFromResponse(it, pageable))
                .orElse(Page.empty(pageable));
    }

    private static Page<VacancyOverview> extractVacancyOverviewsFromResponse(ResponseVacancies response, Pageable pageable) {
        List<ResponseVacanciesItem> items = response.getItems();
        if (items == null) {
            return Page.empty(pageable);
        }

        List<VacancyOverview> result = new LinkedList<>();
        items.forEach((ResponseVacanciesItem it) -> result.add(extractVacancyOverviewFromResponse(it)));

        return new PageImpl<>(result, pageable, response.getFound());
    }

    private static VacancyOverview extractVacancyOverviewFromResponse(ResponseVacanciesItem response) {
        RemoteVacancyOverview result = new RemoteVacancyOverview();
        result.setId(null);
        result.setIdExposed(response.getId());
        result.setOrigin(Origin.REMOTE);
        result.setDatePublished(response.getDatePublished());
        result.setTitle(response.getName());
        result.setSalary(extractSalaryFromResponse(response.getSalary()));

        return result;
    }

    @Override
    public Optional<Vacancy> findById(String id) {
        return headHunterClient.getVacancy(id)
                .map(RemoteVacancyRepositoryImpl::extractVacancyFromResponse);
    }

    private static Vacancy extractVacancyFromResponse(ResponseVacancy response) {
        Vacancy result = new Vacancy();
        result.setIdExposed(response.getId());
        result.setOrigin(Origin.REMOTE);
        result.setDateCreated(response.getCreatedAt());
        result.setDatePublished(response.getPublishedAt());
        result.setTitle(response.getName());
        result.setDescription(response.getDescription());
        result.setSalary(extractSalaryFromResponse(response.getSalary()));
        result.setEmployment(extractEmploymentFromResponse(response.getEmployment()));
        result.setEmployer(extractEmployerFromResponse(response));
        result.addContacts(extractContactsFromResponse(response));

        return result;
    }

    private static Salary extractSalaryFromResponse(ResponseSalary response) {
        if (response == null) {
            return null;
        }

        Salary result = new Salary();

        Long from = response.getFrom();
        if (from != null) {
            result.setFrom(BigDecimal.valueOf(from));
        }

        Long to = response.getTo();
        if (to != null) {
            result.setTo(BigDecimal.valueOf(to));
        }

        result.setCurrency(response.getCurrency());

        return result;
    }

    private static Employment extractEmploymentFromResponse(ResponseEmployment response) {
        if (response == null) {
            return null;
        }

        String id = response.getId();
        if (id == null) {
            return null;
        }

        switch (id) {
            case "full":
                return Employment.FULL_TIME;
            case "part":
                return Employment.PART_TIME;
            default:
                return Employment.OTHER;
        }
    }

    private static Employer extractEmployerFromResponse(ResponseVacancy response) {
        ResponseVacancyEmployer employer = response.getEmployer();
        if (employer == null) {
            return null;
        }

        Employer result = new Employer();
        result.setIdExposed(employer.getId());
        result.setName(employer.getName());

        return result;
    }

    private static List<VacancyContact> extractContactsFromResponse(ResponseVacancy response) {
        ResponseVacancyContact contact = response.getContacts();
        if (contact == null) {
            return null;
        }

        VacancyContact result = new VacancyContact();
        result.setName(contact.getName());
        result.setEmail(contact.getEmail());

        List<ResponseVacancyContactPhone> phones = contact.getPhones();
        if (phones != null && !phones.isEmpty()) {
            ResponseVacancyContactPhone phone = phones.get(0);
            String country = phone.getCountry();
            String city = phone.getCity();
            String number = phone.getNumber();

            if (country != null && city != null && number != null) {
                result.setPhone(country + city + number);
            }
        }

        return Collections.singletonList(result);
    }

    private static class RemoteVacancyOverview implements VacancyOverview {

        private Long id;
        private String idExposed;
        private String origin;
        private Date datePublished;
        private String title;
        private Salary salary;

        @Override
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public String getIdExposed() {
            return idExposed;
        }

        public void setIdExposed(String idExposed) {
            this.idExposed = idExposed;
        }

        @Override
        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        @Override
        public Date getDatePublished() {
            return datePublished;
        }

        public void setDatePublished(Date datePublished) {
            this.datePublished = datePublished;
        }

        @Override
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public Salary getSalary() {
            return salary;
        }

        public void setSalary(Salary salary) {
            this.salary = salary;
        }
    }
}
