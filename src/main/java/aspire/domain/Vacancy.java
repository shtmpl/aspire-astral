package aspire.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "vacancy", indexes = {
        @Index(name = "vacancy_external_id_key", columnList = "external_id", unique = true)})
@SequenceGenerator(name = "vacancy_id_seq")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_id_seq")
    @Column
    private Long id;

    @Column(unique = true)
    private Long externalId;

    @Column
    private Date dateSynchronized;

    @NotBlank
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal salaryFrom;

    @Column(precision = 10, scale = 2)
    private BigDecimal salaryTo;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column
    private Employment employment;

    @ManyToOne
    @JoinColumn(name = "vacancy_owner_id")
    private VacancyOwner owner;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VacancyContact> contacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Date getDateSynchronized() {
        return dateSynchronized;
    }

    public void setDateSynchronized(Date dateSynchronized) {
        this.dateSynchronized = dateSynchronized;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public VacancyOwner getOwner() {
        return owner;
    }

    public void setOwner(VacancyOwner owner) {
        this.owner = owner;
    }

    public Set<VacancyContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<VacancyContact> contacts) {
        this.contacts = contacts;
    }

}
