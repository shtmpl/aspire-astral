package aspire.astral.repository;

import aspire.astral.domain.Employer;
import aspire.astral.domain.Employment;
import aspire.astral.domain.Origin;
import aspire.astral.domain.Salary;
import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyContact;
import aspire.astral.integration.HeadHunterClient;
import aspire.astral.integration.response.ResponseVacancies;
import aspire.astral.integration.response.ResponseVacanciesItem;
import aspire.astral.integration.response.ResponseVacancy;
import aspire.astral.integration.response.ResponseVacancyContact;
import aspire.astral.integration.response.ResponseVacancyContactPhone;
import aspire.astral.integration.response.ResponseVacancyEmployer;
import aspire.astral.integration.response.ResponseVacancyEmployment;
import aspire.astral.integration.response.ResponseVacancySalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class RemoteVacancyRepositoryImpl implements RemoteVacancyRepository {

    private final HeadHunterClient headHunterClient;

    @Autowired
    public RemoteVacancyRepositoryImpl(HeadHunterClient headHunterClient) {
        this.headHunterClient = headHunterClient;
    }

    @Override
    public Page<Vacancy> findAll(Pageable pageable) {


        return headHunterClient.getVacancies(pageable).map((ResponseVacancies vacancies) -> {
            List<ResponseVacanciesItem> items = vacancies.getItems();
            if (items == null) {
                return Page.<Vacancy>empty(pageable);
            }

            List<Vacancy> result = new LinkedList<>();
            items.forEach((ResponseVacanciesItem item) ->
                    headHunterClient.getVacancy(item.getId())
                            .ifPresent((ResponseVacancy vacancy) -> result.add(extractVacancyFromResponse(vacancy))));

            return new PageImpl<>(result, pageable, vacancies.getFound());
        }).orElse(Page.empty(pageable));
    }

    @Override
    public List<Vacancy> findAllByTitleContaining(String title, Pageable pageable) {
        List<Vacancy> result = new LinkedList<>();

        headHunterClient.getVacancies(title, pageable).ifPresent((ResponseVacancies vacancies) -> {
            List<ResponseVacanciesItem> items = vacancies.getItems();
            if (items == null) {
                return;
            }

            items.forEach((ResponseVacanciesItem item) ->
                    headHunterClient.getVacancy(item.getId())
                            .ifPresent((ResponseVacancy vacancy) -> result.add(extractVacancyFromResponse(vacancy))));
        });

        return result;
    }

    @Override
    public Optional<Vacancy> findById(String id) {
        return headHunterClient.getVacancy(id)
                .map(RemoteVacancyRepositoryImpl::extractVacancyFromResponse);
    }

    private static Vacancy extractVacancyFromResponse(ResponseVacancy response) {
        Vacancy result = new Vacancy();
        result.setIdExternal(response.getId());
        result.setOrigin(Origin.REMOTE);
        result.setDateCreated(response.getCreatedAt());
        result.setTitle(response.getName());
        result.setDescription(response.getDescription());
        result.setSalary(extractSalaryFromResponse(response));
        result.setEmployment(extractEmploymentFromResponse(response));
        result.setEmployer(extractEmployerFromResponse(response));
        result.setContacts(extractContactsFromResponse(response));

        return result;
    }

    private static Salary extractSalaryFromResponse(ResponseVacancy response) {
        ResponseVacancySalary salary = response.getSalary();
        if (salary == null) {
            return null;
        }

        Salary result = new Salary();

        Long from = salary.getFrom();
        if (from != null) {
            result.setFrom(BigDecimal.valueOf(from));
        }

        Long to = salary.getTo();
        if (to != null) {
            result.setTo(BigDecimal.valueOf(to));
        }

        result.setCurrency(salary.getCurrency());

        return result;
    }

    private static Employment extractEmploymentFromResponse(ResponseVacancy response) {
        ResponseVacancyEmployment employment = response.getEmployment();
        if (employment == null) {
            return null;
        }

        String id = employment.getId();
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
        result.setIdExternal(employer.getId());
        result.setName(employer.getName());

        return result;
    }

    private static Set<VacancyContact> extractContactsFromResponse(ResponseVacancy response) {
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

        return Collections.singleton(result);
    }
}
