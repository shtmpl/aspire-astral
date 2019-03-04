package aspire.controller;

import aspire.controller.request.InputVacancy;
import aspire.domain.Employment;
import aspire.domain.Origin;
import aspire.domain.Vacancy;
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
import java.util.List;

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
                                        @Valid @RequestBody InputVacancy request) {
        Vacancy result = mapToVacancy(request);

        return ResponseEntity.ok(vacancyService.createVacancy(origin, result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable String id,
                                          @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin,
                                          @Valid @RequestBody InputVacancy request) {
        Vacancy result = mapToVacancy(request);

        return ResponseEntity.ok(vacancyService.updateVacancy(origin, id, result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> delete(@PathVariable String id,
                                          @RequestParam(defaultValue = DEFAULT_ORIGIN) String origin) {
        Vacancy result = vacancyService.deleteVacancy(origin, id);

        return ResponseEntity.ok(result);
    }

    private static Vacancy mapToVacancy(InputVacancy request) {
        Vacancy result = new Vacancy();
        result.setTitle(request.getTitle());
        result.setDescription(request.getDescription());
        result.setEmployment(Employment.fromString(request.getEmployment()));

        return result;
    }

}
