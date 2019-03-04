package aspire.controller;

import aspire.controller.request.RequestEmployer;
import aspire.controller.request.RequestSalary;
import aspire.controller.request.RequestVacancy;
import aspire.controller.request.RequestVacancyContact;
import aspire.domain.Employer;
import aspire.domain.Employment;
import aspire.domain.Origin;
import aspire.domain.Salary;
import aspire.domain.Vacancy;
import aspire.domain.VacancyContact;
import aspire.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/vacancy")
public class VacancyController {

    private static final String DEFAULT_ORIGIN = Origin.LOCAL;

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_SIZE = "10";

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("")
    public ResponseEntity<List<Vacancy>> index(@RequestParam(defaultValue = DEFAULT_ORIGIN) String origin,
                                               @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(required = false, defaultValue = DEFAULT_SIZE) int size) {
        List<Vacancy> result = vacancyService.findVacancies(origin, PageRequest.of(page, size));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vacancy>> lookup(@RequestParam(defaultValue = DEFAULT_ORIGIN) String origin,
                                                @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                                                @RequestParam(required = false, defaultValue = DEFAULT_SIZE) int size,
                                                @RequestParam MultiValueMap<String, String> params) {
        if (params.containsKey("title.like")) {
            List<Vacancy> result = vacancyService.findVacanciesByTitleLike(origin, params.getFirst("title.like"), PageRequest.of(page, size));

            return ResponseEntity.ok(result);
        }

        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> show(@PathVariable String id,
                                        @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin) {
        Vacancy result = vacancyService.findVacancyById(origin, id);

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<Vacancy> save(@RequestParam(defaultValue = DEFAULT_ORIGIN) String origin,
                                        @Valid @RequestBody RequestVacancy request) {
        Vacancy result = extractVacancyFromRequest(request);

        return ResponseEntity.ok(vacancyService.createVacancy(origin, result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable String id,
                                          @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin,
                                          @Valid @RequestBody RequestVacancy request) {
        Vacancy result = extractVacancyFromRequest(request);

        return ResponseEntity.ok(vacancyService.updateVacancy(origin, id, result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> delete(@PathVariable String id,
                                          @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin) {
        Vacancy result = vacancyService.deleteVacancy(origin, id);

        return ResponseEntity.ok(result);
    }

    private static Vacancy extractVacancyFromRequest(RequestVacancy request) {
        Vacancy result = new Vacancy();
        result.setTitle(request.getTitle());
        result.setDescription(request.getDescription());
        result.setSalary(extractSalaryFromRequest(request));
        result.setEmployment(Employment.fromString(request.getEmployment()));
        result.setEmployer(extractEmployerFromRequest(request));
        result.setContacts(extractContactsFromRequest(request));

        return result;
    }

    private static Salary extractSalaryFromRequest(RequestVacancy request) {
        RequestSalary salary = request.getSalary();
        if (salary == null) {
            return null;
        }

        Salary result = new Salary();
        result.setCurrency(salary.getCurrency());
        result.setFrom(salary.getFrom());
        result.setTo(salary.getTo());

        return result;
    }

    private static Employer extractEmployerFromRequest(RequestVacancy request) {
        RequestEmployer employer = request.getEmployer();
        if (employer == null) {
            return null;
        }

        Employer result = new Employer();
        result.setName(employer.getName());

        return result;
    }

    private static Set<VacancyContact> extractContactsFromRequest(RequestVacancy request) {
        Set<VacancyContact> result = new LinkedHashSet<>();

        List<RequestVacancyContact> contacts = request.getContacts();
        if (contacts == null || contacts.isEmpty()) {
            return result;
        }

        for (RequestVacancyContact contact : contacts) {
            result.add(extractVacancyContactFromRequest(contact));
        }

        return result;
    }

    private static VacancyContact extractVacancyContactFromRequest(RequestVacancyContact request) {
        if (request == null) {
            return null;
        }

        VacancyContact result = new VacancyContact();
        result.setName(request.getName());
        result.setEmail(request.getEmail());
        result.setPhone(request.getPhone());

        return result;
    }

}
