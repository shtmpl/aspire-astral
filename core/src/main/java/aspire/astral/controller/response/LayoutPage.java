package aspire.astral.controller.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class LayoutPage<Data> {

    private Integer page;
    private Integer size;
    private Long total;
    private Map<String, Data> fields = new LinkedHashMap<>();

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @JsonAnyGetter
    public Map<String, Data> get() {
        return fields;
    }

    @JsonAnySetter
    public void set(String key, Data data) {
        this.fields.put(key, data);
    }
}
