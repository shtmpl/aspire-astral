package aspire.astral.controller;

import aspire.astral.controller.request.RequestEmployer;
import aspire.astral.controller.request.RequestSalary;
import aspire.astral.controller.request.RequestVacancy;
import aspire.astral.controller.request.RequestVacancyContact;
import aspire.astral.controller.response.LayoutPage;
import aspire.astral.controller.response.ResponseEmployer;
import aspire.astral.controller.response.ResponseSalary;
import aspire.astral.controller.response.ResponseVacancy;
import aspire.astral.controller.response.ResponseVacancyContact;
import aspire.astral.controller.response.ResponseVacancyOverview;
import aspire.astral.domain.Employer;
import aspire.astral.domain.Employment;
import aspire.astral.domain.Origin;
import aspire.astral.domain.Salary;
import aspire.astral.domain.Vacancy;
import aspire.astral.domain.VacancyContact;
import aspire.astral.domain.VacancyOverview;
import aspire.astral.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @GetMapping({"/{repository}", "/{repository}/index"})
    public ResponseEntity<LayoutPage<List<ResponseVacancyOverview>>> index(@PathVariable String repository,
                                                                           @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                                                                           @RequestParam(required = false, defaultValue = DEFAULT_SIZE) int size) {
        Page<VacancyOverview> overviews = vacancyService.findVacancyOverviews(
                repository,
                PageRequest.of(page, size, Sort.by("datePublished").descending()));

        return ResponseEntity.ok(extractResponseFromPage(overviews, VacancyController::extractResponseFromVacancyOverview));
    }


    @GetMapping("/{repository}/search")
    public ResponseEntity<LayoutPage<List<ResponseVacancyOverview>>> search(@PathVariable String repository,
                                                                            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                                                                            @RequestParam(required = false, defaultValue = DEFAULT_SIZE) int size,
                                                                            @RequestParam MultiValueMap<String, String> params) {
        if (params.containsKey("title.like")) {
            Page<VacancyOverview> overviews = vacancyService.findVacancyOverviewsByTitleLike(
                    repository,
                    params.getFirst("title.like"),
                    PageRequest.of(page, size, Sort.by("datePublished").descending()));

            return ResponseEntity.ok(extractResponseFromPage(overviews, VacancyController::extractResponseFromVacancyOverview));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{repository}/{id}")
    public ResponseEntity<ResponseVacancy> show(@PathVariable String repository,
                                                @PathVariable String id,
                                                @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin) {
        Vacancy vacancy = vacancyService.findVacancy(repository, id, origin);

        return ResponseEntity.ok(extractResponseFromVacancy(vacancy));
    }

    @GetMapping("/{repository}/{id}/acquire")
    public ResponseEntity<ResponseVacancy> acquire(@PathVariable String repository,
                                                   @PathVariable String id,
                                                   @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin) {
        Vacancy vacancy = vacancyService.acquireVacancy(repository, id, origin);

        return ResponseEntity.ok(extractResponseFromVacancy(vacancy));
    }

    @PostMapping("/{repository}")
    public ResponseEntity<ResponseVacancy> save(@PathVariable String repository,
                                                @Valid @RequestBody RequestVacancy request) {
        Vacancy vacancy = vacancyService.createVacancy(repository, extractVacancyFromRequest(request));

        return ResponseEntity.ok(extractResponseFromVacancy(vacancy));
    }

    @PutMapping("/{repository}/{id}")
    public ResponseEntity<ResponseVacancy> update(@PathVariable String repository,
                                                  @PathVariable String id,
                                                  @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin,
                                                  @Valid @RequestBody RequestVacancy request) {
        Vacancy vacancy = vacancyService.updateVacancy(repository, id, origin, extractVacancyFromRequest(request));

        return ResponseEntity.ok(extractResponseFromVacancy(vacancy));
    }

    @DeleteMapping("/{repository}/{id}")
    public ResponseEntity<ResponseVacancy> delete(@PathVariable String repository,
                                                  @PathVariable String id,
                                                  @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin) {
        Vacancy vacancy = vacancyService.deleteVacancy(repository, id, origin);

        return ResponseEntity.ok(extractResponseFromVacancy(vacancy));
    }


    // Request processing

    private static Vacancy extractVacancyFromRequest(RequestVacancy request) {
        Vacancy result = new Vacancy();
        result.setDatePublished(request.getDatePublished());
        result.setTitle(request.getTitle());
        result.setDescription(request.getDescription());
        result.setSalary(extractSalaryFromRequest(request));
        result.setEmployment(Employment.fromString(request.getEmployment()));
        result.setEmployer(extractEmployerFromRequest(request));
        result.addContacts(extractContactsFromRequest(request));

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


    // Response processing

    private static <X, F> LayoutPage<List<F>> extractResponseFromPage(Page<X> page, Function<X, F> f) {
        LayoutPage<List<F>> result = new LayoutPage<>();
        result.setPage(page.getNumber());
        result.setSize(page.getSize());
        result.setTotal(page.getTotalElements());
        result.set("slice", page.getContent().stream().map(f).collect(Collectors.toList()));

        return result;
    }

    private static ResponseVacancyOverview extractResponseFromVacancyOverview(VacancyOverview overview) {
        ResponseVacancyOverview result = new ResponseVacancyOverview();
        result.setId(overview.getIdExposed());
        result.setOrigin(overview.getOrigin());
        result.setDatePublished(overview.getDatePublished());
        result.setTitle(overview.getTitle());
        result.setSalary(extractResponseFromSalary(overview.getSalary()));
        result.setEmployer(extractResponseFromEmployer(overview.getEmployer()));

        return result;
    }

    private static ResponseVacancy extractResponseFromVacancy(Vacancy vacancy) {
        ResponseVacancy result = new ResponseVacancy();
        result.setId(vacancy.getIdExposed());
        result.setOrigin(vacancy.getOrigin());
        result.setDateCreated(vacancy.getDateCreated());
        result.setDatePublished(vacancy.getDatePublished());
        result.setTitle(vacancy.getTitle());
        result.setDescription(vacancy.getDescription());
        result.setSalary(extractResponseFromSalary(vacancy.getSalary()));
        result.setEmployment(vacancy.getEmployment());
        result.setEmployer(extractResponseFromEmployer(vacancy.getEmployer()));
        result.setContacts(extractResponseFromVacancyContacts(vacancy.getContacts()));

        return result;
    }

    private static ResponseSalary extractResponseFromSalary(Salary salary) {
        if (salary == null) {
            return null;
        }

        ResponseSalary result = new ResponseSalary();
        result.setCurrency(salary.getCurrency());
        result.setFrom(salary.getFrom());
        result.setTo(salary.getTo());

        return result;
    }

    private static ResponseEmployer extractResponseFromEmployer(Employer employer) {
        if (employer == null) {
            return null;
        }

        ResponseEmployer result = new ResponseEmployer();
        result.setId(employer.getIdExposed());
        result.setOrigin(employer.getOrigin());
        result.setName(employer.getName());

        return result;
    }

    private static List<ResponseVacancyContact> extractResponseFromVacancyContacts(Set<VacancyContact> contacts) {
        if (contacts == null) {
            return Collections.emptyList();
        }

        return contacts
                .stream()
                .map(VacancyController::extractResponseFromVacancyContact)
                .collect(Collectors.toList());
    }

    private static ResponseVacancyContact extractResponseFromVacancyContact(VacancyContact contact) {
        if (contact == null) {
            return null;
        }

        ResponseVacancyContact result = new ResponseVacancyContact();
        result.setId(contact.getIdExposed());
        result.setName(contact.getName());
        result.setEmail(contact.getEmail());
        result.setPhone(contact.getPhone());

        return result;
    }
}
