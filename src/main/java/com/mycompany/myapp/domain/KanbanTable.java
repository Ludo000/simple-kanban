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
 * A KanbanTable.
 */
@Entity
@Table(name = "kanban_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KanbanTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @OneToMany(mappedBy = "kanbanTable")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<KanbanColumn> tables = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "kanbanTables", allowSetters = true)
    private User user;

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

    public KanbanTable name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public KanbanTable description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public KanbanTable creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public KanbanTable modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Set<KanbanColumn> getTables() {
        return tables;
    }

    public KanbanTable tables(Set<KanbanColumn> kanbanColumns) {
        this.tables = kanbanColumns;
        return this;
    }

    public KanbanTable addTables(KanbanColumn kanbanColumn) {
        this.tables.add(kanbanColumn);
        kanbanColumn.setKanbanTable(this);
        return this;
    }

    public KanbanTable removeTables(KanbanColumn kanbanColumn) {
        this.tables.remove(kanbanColumn);
        kanbanColumn.setKanbanTable(null);
        return this;
    }

    public void setTables(Set<KanbanColumn> kanbanColumns) {
        this.tables = kanbanColumns;
    }

    public User getUser() {
        return user;
    }

    public KanbanTable user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KanbanTable)) {
            return false;
        }
        return id != null && id.equals(((KanbanTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KanbanTable{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            "}";
    }
}
