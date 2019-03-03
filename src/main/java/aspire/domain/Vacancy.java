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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "vacancy",
        indexes = {
                @Index(name = "vacancy_id_external_key", columnList = "idExternal", unique = true)
        })
@SequenceGenerator(name = "vacancy_id_seq")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_id_seq")
    @Column
    private Long id;

    @Column
    private String idExternal;

    @Enumerated(EnumType.STRING)
    @Column
    private Origin origin;

    @Column
    private Date dateCreated;

    @Column
    private Date datePublished;

    @NotBlank
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal salaryFrom;

    @Column(precision = 10, scale = 2)
    private BigDecimal salaryTo;

    @Enumerated(EnumType.STRING)
    @Column
    private Employment employment;

    @ManyToOne
    @JoinColumn(name = "vacancy_owner_id")
    private Employer employer;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VacancyContact> contacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdExternal() {
        return idExternal;
    }

    public void setIdExternal(String idExternal) {
        this.idExternal = idExternal;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(BigDecimal salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public BigDecimal getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(BigDecimal salaryTo) {
        this.salaryTo = salaryTo;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Set<VacancyContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<VacancyContact> contacts) {
        this.contacts = contacts;
    }

}
