package aspire.astral.integration.response;

import java.util.List;

public class ResponseVacancyContact {

    private String name;
    private String email;
    private List<ResponseVacancyContactPhone> phones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ResponseVacancyContactPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<ResponseVacancyContactPhone> phones) {
        this.phones = phones;
    }
}
