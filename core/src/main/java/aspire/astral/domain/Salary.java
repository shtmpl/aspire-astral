package aspire.astral.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Salary {

    @Column(length = 3)
    private String currency;

    @Column(precision = 10, scale = 2)
    private BigDecimal from;

    @Column(precision = 10, scale = 2)
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
