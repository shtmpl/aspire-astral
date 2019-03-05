package aspire.astral.controller.request;

import java.math.BigDecimal;

public class RequestSalary {

    private String currency;
    private BigDecimal from;
    private BigDecimal to;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getFrom() {
        return from;
    }

    public void setFrom(BigDecimal from) {
        this.from = from;
    }

    public BigDecimal getTo() {
        return to;
    }

    public void setTo(BigDecimal to) {
        this.to = to;
    }
}
