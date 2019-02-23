package aspire.service;

import aspire.domain.Vacancy;
import aspire.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultVacancyService implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Autowired
    public DefaultVacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public Vacancy getVacancy(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException(String.format("No vacancy found for id: %d", id)));
    }

    @Override
    public List<Vacancy> findVacancies() {
        return vacancyRepository.findAll();
    }

    @Override
    public List<Vacancy> findVacanciesByTitle(String title) {
        return vacancyRepository.findAllByTitle(title);
    }

}
