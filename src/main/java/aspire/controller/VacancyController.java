package aspire.controller;

import aspire.controller.command.IssueRequestCommand;
import aspire.controller.command.ReadRequestCommand;
import aspire.domain.Vacancy;
import aspire.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<List<Vacancy>> index() {
        return new ResponseEntity<>(vacancyService.findVacancies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> show(@PathVariable Long id) {
        return new ResponseEntity<>(vacancyService.getVacancy(id), HttpStatus.OK);
    }

}
