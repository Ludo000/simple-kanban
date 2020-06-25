package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TaskCardType.
 */
@Entity
@Table(name = "task_card_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskCardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "types", allowSetters = true)
    private TaskCard taskCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TaskCardType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskCard getTaskCard() {
        return taskCard;
    }

    public TaskCardType taskCard(TaskCard taskCard) {
        this.taskCard = taskCard;
        return this;
    }

    public void setTaskCard(TaskCard taskCard) {
        this.taskCard = taskCard;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskCardType)) {
            return false;
        }
        return id != null && id.equals(((TaskCardType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCardType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
