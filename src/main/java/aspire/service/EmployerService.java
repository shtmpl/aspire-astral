package aspire.service;

import aspire.domain.Employer;

public interface EmployerService {

    Employer findOrSaveEmployer(Employer employer);

}
