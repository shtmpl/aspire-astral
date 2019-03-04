package aspire.repository;

import aspire.domain.Employer;
import aspire.domain.Employment;
import aspire.domain.Origin;
import aspire.domain.Salary;
import aspire.domain.Vacancy;
import aspire.domain.VacancyContact;
import aspire.integration.HeadHunterClient;
import aspire.integration.response.ResponseVacancies;
import aspire.integration.response.ResponseVacanciesItem;
import aspire.integration.response.ResponseVacancy;
import aspire.integration.response.ResponseVacancyContact;
import aspire.integration.response.ResponseVacancyContactPhone;
import aspire.integration.response.ResponseVacancyEmployer;
import aspire.integration.response.ResponseVacancyEmployment;
import aspire.integration.response.ResponseVacancySalary;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Vacancy> findAll(Pageable pageable) {
        List<Vacancy> result = new LinkedList<>();

        headHunterClient.getVacancies(pageable).ifPresent((ResponseVacancies vacancies) -> {
            List<ResponseVacanciesItem> items = vacancies.getItems();
            if (items == null) {
                return;
            }

            items.forEach((ResponseVacanciesItem item) ->
                    headHunterClient.getVacancy(item.getId())
                            .ifPresent((ResponseVacancy vacancy) -> result.add(extractVacancy(vacancy))));
        });

        return result;
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
                            .ifPresent((ResponseVacancy vacancy) -> result.add(extractVacancy(vacancy))));
        });

        return result;
    }

    @Override
    public Optional<Vacancy> findById(String id) {
        return headHunterClient.getVacancy(id)
                .map(RemoteVacancyRepositoryImpl::extractVacancy);
    }

    private static Vacancy extractVacancy(ResponseVacancy response) {
        Vacancy result = new Vacancy();
        result.setIdExternal(response.getId());
        result.setOrigin(Origin.REMOTE);
        result.setDateCreated(response.getCreatedAt());
        result.setTitle(response.getName());
        result.setDescription(response.getDescription());
        result.setSalary(extractSalary(response));
        result.setEmployment(extractEmployment(response));
        result.setEmployer(extractEmployer(response));
        result.setContacts(extractVacancyContacts(response));

        return result;
    }

    private static Salary extractSalary(ResponseVacancy response) {
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

    private static Employment extractEmployment(ResponseVacancy response) {
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

    private static Employer extractEmployer(ResponseVacancy response) {
        ResponseVacancyEmployer employer = response.getEmployer();
        if (employer == null) {
            return null;
        }

        Employer result = new Employer();
        result.setIdExternal(employer.getId());
        result.setName(employer.getName());

        return result;
    }

    private static Set<VacancyContact> extractVacancyContacts(ResponseVacancy response) {
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
