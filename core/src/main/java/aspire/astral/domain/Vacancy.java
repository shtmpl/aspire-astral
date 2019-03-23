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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "vacancy",
        indexes = {
                @Index(name = "vacancy_id_exposed_origin_key", columnList = "idExposed, origin", unique = true)
        })
@SequenceGenerator(name = "vacancy_id_seq")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_id_seq")
    @Column
    private Long id;

    @Column
    private String idExposed;

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
    @JoinColumn(name = "owner_id")
    private Employer employer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    private Set<VacancyContact> contacts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdExposed() {
        return idExposed;
    }

    public void setIdExposed(String idExposed) {
        this.idExposed = idExposed;
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

    public void addContact(VacancyContact contact) {
        if (contact == null) {
            return;
        }

        this.contacts.add(contact);
    }

    public void addContacts(Collection<VacancyContact> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            return;
        }

        this.contacts.addAll(contacts);
    }

    public void clearContacts() {
        this.contacts.clear();
    }

    @PrePersist
    public void onPrePersist() {
        if (this.dateCreated == null) {
            this.dateCreated = Date.from(Instant.now());
        }

        if (this.idExposed == null) {
            this.idExposed = UUID.randomUUID().toString();
        }

        if (this.origin == null) {
            this.origin = Origin.LOCAL;
        }
    }
}
