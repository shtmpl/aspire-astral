package aspire.astral.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "vacancy_contact")
@SequenceGenerator(name = "vacancy_contact_id_seq")
public class VacancyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_contact_id_seq")
    @Column
    private Long id;

    @Column
    private String idExposed;

    @NotBlank
    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @PrePersist
    public void onPrePersist() {
        if (this.idExposed == null) {
            this.idExposed = UUID.randomUUID().toString();
        }
    }
}
