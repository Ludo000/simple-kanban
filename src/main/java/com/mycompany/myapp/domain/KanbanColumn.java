package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A KanbanColumn.
 */
@Entity
@Table(name = "kanban_column")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KanbanColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @OneToMany(mappedBy = "kanbanColumn")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TaskCard> columns = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "tables", allowSetters = true)
    private KanbanTable kanbanTable;

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

    public KanbanColumn name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public KanbanColumn creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public KanbanColumn modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Set<TaskCard> getColumns() {
        return columns;
    }

    public KanbanColumn columns(Set<TaskCard> taskCards) {
        this.columns = taskCards;
        return this;
    }

    public KanbanColumn addColumns(TaskCard taskCard) {
        this.columns.add(taskCard);
        taskCard.setKanbanColumn(this);
        return this;
    }

    public KanbanColumn removeColumns(TaskCard taskCard) {
        this.columns.remove(taskCard);
        taskCard.setKanbanColumn(null);
        return this;
    }

    public void setColumns(Set<TaskCard> taskCards) {
        this.columns = taskCards;
    }

    public KanbanTable getKanbanTable() {
        return kanbanTable;
    }

    public KanbanColumn kanbanTable(KanbanTable kanbanTable) {
        this.kanbanTable = kanbanTable;
        return this;
    }

    public void setKanbanTable(KanbanTable kanbanTable) {
        this.kanbanTable = kanbanTable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KanbanColumn)) {
            return false;
        }
        return id != null && id.equals(((KanbanColumn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KanbanColumn{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            "}";
    }
}
