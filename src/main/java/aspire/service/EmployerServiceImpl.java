package aspire.service;

import aspire.domain.Employer;
import aspire.repository.LocalEmployerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final LocalEmployerRepository localEmployerRepository;

    public EmployerServiceImpl(LocalEmployerRepository localEmployerRepository) {
        this.localEmployerRepository = localEmployerRepository;
    }

    @Override
    public Employer findOrSaveEmployer(Employer employer) {
        String name = employer.getName();
        return localEmployerRepository.findByName(name)
                .orElseGet(() -> {
                    Employer result = new Employer();
                    result.setName(name);

                    return localEmployerRepository.save(employer);
                });
    }

}
