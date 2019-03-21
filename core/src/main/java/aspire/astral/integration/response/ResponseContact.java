package aspire.astral.integration.response;

import java.util.List;

public class ResponseContact {

    private String name;
    private String email;
    private List<ResponseContactPhone> phones;

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

    public List<ResponseContactPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<ResponseContactPhone> phones) {
        this.phones = phones;
    }
}
