package aspire.repository;

import aspire.domain.Employer;
import aspire.domain.Employment;
import aspire.domain.Origin;
import aspire.domain.Vacancy;
import aspire.domain.VacancyContact;
import aspire.integration.HeadHunterClient;
import aspire.integration.response.ResponseVacancies;
import aspire.integration.response.ResponseVacanciesItem;
import aspire.integration.response.ResponseVacancy;
import aspire.integration.response.ResponseVacancyContact;
import aspire.integration.response.ResponseVacancyContactPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class RemoteVacancyRepositoryImplementation implements RemoteVacancyRepository {

    private final HeadHunterClient headHunterClient;

    @Autowired
    public RemoteVacancyRepositoryImplementation(HeadHunterClient headHunterClient) {
        this.headHunterClient = headHunterClient;
    }

    @Override
    public List<Vacancy> findAll() {
        List<Vacancy> result = new LinkedList<>();

        headHunterClient.getVacancies().ifPresent((ResponseVacancies responseVacancies) -> {
            for (ResponseVacanciesItem item : responseVacancies.getItems()) {
                String id = item.getId();

                headHunterClient.getVacancy(id)
                        .ifPresent((ResponseVacancy responseVacancy) -> result.add(extractVacancy(responseVacancy)));
            }
        });

        return result;
    }

    @Override
    public List<Vacancy> findAllByTitleContaining(String title) {
        return null;
    }

    @Override
    public Optional<Vacancy> findById(String id) {
        return headHunterClient.getVacancy(id)
                .map(RemoteVacancyRepositoryImplementation::extractVacancy);
    }

    private static Vacancy extractVacancy(ResponseVacancy response) {
        Vacancy result = new Vacancy();
        result.setIdExternal(response.getId());
        result.setOrigin(Origin.REMOTE);
        result.setDateCreated(response.getCreatedAt());
        result.setTitle(response.getName());
        result.setDescription(response.getDescription());
        result.setSalaryFrom(extractVacancySalaryFrom(response));
        result.setSalaryTo(extractVacancySalaryTo(response));
        result.setEmployment(extractEmployment(response));
        result.setEmployer(extractEmployer(response));
        result.setContacts(extractVacancyContacts(response));

        return result;
    }

    private static BigDecimal extractVacancySalaryFrom(ResponseVacancy response) {
        if (response.getSalary() == null) {
            return null;
        }

        Long result = response.getSalary().getFrom();
        if (result == null) {
            return null;
        }

        return BigDecimal.valueOf(result);
    }

    private static BigDecimal extractVacancySalaryTo(ResponseVacancy response) {
        if (response.getSalary() == null) {
            return null;
        }

        Long result = response.getSalary().getTo();
        if (result == null) {
            return null;
        }

        return BigDecimal.valueOf(result);
    }

    private static Employment extractEmployment(ResponseVacancy response) {
        if (response.getEmployment() == null || response.getEmployment().getName() == null) {
            return null;
        }

        switch (response.getEmployment().getId()) {
            case "full":
                return Employment.FULL_TIME;
            case "part":
                return Employment.PART_TIME;
            default:
                return Employment.OTHER;
        }
    }

    private static Employer extractEmployer(ResponseVacancy response) {
        if (response.getEmployer() == null) {
            return null;
        }

        Employer result = new Employer();
        result.setIdExternal(response.getEmployer().getId());
        result.setName(response.getEmployer().getName());

        return result;
    }

    private static Set<VacancyContact> extractVacancyContacts(ResponseVacancy response) {
        ResponseVacancyContact responseContact = response.getContacts();
        if (responseContact == null) {
            return null;
        }

        VacancyContact contact = new VacancyContact();
        contact.setName(responseContact.getName());
        contact.setEmail(responseContact.getEmail());

        List<ResponseVacancyContactPhone> responsePhones = responseContact.getPhones();
        if (responsePhones != null && !responsePhones.isEmpty()) {
            ResponseVacancyContactPhone responsePhone = responsePhones.get(0);
            String country = responsePhone.getCountry();
            String city = responsePhone.getCity();
            String number = responsePhone.getNumber();

            if (country != null && city != null && number != null) {
                contact.setPhone(country + city + number);
            }
        }

        return Collections.singleton(contact);
    }

}
