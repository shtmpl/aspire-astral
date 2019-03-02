package aspire.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vacancy_owner", indexes = {
        @Index(name = "vacancy_owner_external_id_key", columnList = "external_id", unique = true)})
@SequenceGenerator(name = "vacancy_owner_id_seq")
public class VacancyOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_owner_id_seq")
    @Column
    private Long id;

    @Column(unique = true)
    private Long externalId;

    @Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
