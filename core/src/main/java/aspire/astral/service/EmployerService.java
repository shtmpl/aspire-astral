package aspire.astral.service;

import aspire.astral.domain.Employer;

/**
 * Service for {@link Employer} related operations.
 */
public interface EmployerService {

    Employer findOrSaveEmployer(Employer employer);
}
