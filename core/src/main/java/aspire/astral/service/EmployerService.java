package aspire.astral.service;

import aspire.astral.domain.Employer;

public interface EmployerService {

    Employer findOrSaveEmployer(Employer employer);
}
