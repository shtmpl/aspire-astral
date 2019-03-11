package aspire.astral.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "employer",
        indexes = {
                @Index(name = "employer_id_exposed_key", columnList = "idExposed", unique = true),
                @Index(name = "employer_name_key", columnList = "name", unique = true)
        })
@SequenceGenerator(name = "employer_id_seq")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employer_id_seq")
    @Column
    private Long id;

    @Column
    private String idExposed;

    @NotBlank
    @Column
    private String name;

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
}
