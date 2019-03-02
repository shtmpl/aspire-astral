package aspire.controller;

import aspire.controller.request.InputVacancy;
import aspire.domain.Employment;
import aspire.domain.Vacancy;
import aspire.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.List;

@RestController
@RequestMapping("/api/vacancy")
public class VacancyController {

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("")
    public ResponseEntity<List<Vacancy>> index(@RequestParam(required = false) String title) {
        List<Vacancy> vacancies = title == null ?
                vacancyService.findVacancies() :
                vacancyService.findVacanciesByTitleLike(title);
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> show(@PathVariable Long id) {
        return new ResponseEntity<>(vacancyService.getVacancy(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Vacancy> save(@Valid @RequestBody InputVacancy request) {
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setEmployment(Employment.fromString(request.getEmployment()));

        return new ResponseEntity<>(vacancyService.createVacancy(request), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable Long id,
                                          @Valid @RequestBody InputVacancy request) {
        return new ResponseEntity<>(vacancyService.updateVacancy(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> delete(@PathVariable Long id) {
        return new ResponseEntity<>(vacancyService.deleteVacancy(id), HttpStatus.OK);
    }

}
