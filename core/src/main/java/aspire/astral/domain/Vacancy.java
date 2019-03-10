package aspire.astral.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "vacancy",
        indexes = {
                @Index(name = "vacancy_id_external_origin_key", columnList = "idExternal, origin", unique = true)
        })
@SequenceGenerator(name = "vacancy_id_seq")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_id_seq")
    @Column
    private Long id;

    @Column
    private String idExternal;

    @Column
    private String origin;

    @Column
    private Date dateCreated;

    @Column
    private Date datePublished;

    @NotBlank
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "currency", column = @Column(name = "salary_currency", length = 3)),
            @AttributeOverride(name = "from", column = @Column(name = "salary_from", precision = 10, scale = 2)),
            @AttributeOverride(name = "to", column = @Column(name = "salary_to", precision = 10, scale = 2))
    })
    private Salary salary;

    @Enumerated(EnumType.STRING)
    @Column
    private Employment employment;

    @ManyToOne
    @JoinColumn(name = "vacancy_owner_id")
    private Employer employer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vacancy_contact_id")
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
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

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
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
