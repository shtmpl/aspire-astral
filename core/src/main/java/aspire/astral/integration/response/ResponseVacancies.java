package aspire.astral.integration.response;

import java.util.List;

public class ResponseVacancies {

    private List<ResponseVacanciesItem> items;
    private Long found;

    public List<ResponseVacanciesItem> getItems() {
        return items;
    }

    public void setItems(List<ResponseVacanciesItem> items) {
        this.items = items;
    }

    public Long getFound() {
        return found;
    }

    public void setFound(Long found) {
        this.found = found;
    }
}
