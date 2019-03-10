package aspire.astral.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Salary salary = (Salary) other;

        return Objects.equals(currency, salary.currency) &&
                Objects.equals(from, salary.from) &&
                Objects.equals(to, salary.to);
    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);

        return result;
    }
}
