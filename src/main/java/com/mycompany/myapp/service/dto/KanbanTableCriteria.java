package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.KanbanTable} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.KanbanTableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kanban-tables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KanbanTableCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private InstantFilter creationDate;

    private InstantFilter modificationDate;

    private LongFilter tablesId;

    private LongFilter userId;

    public KanbanTableCriteria() {
    }

    public KanbanTableCriteria(KanbanTableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.modificationDate = other.modificationDate == null ? null : other.modificationDate.copy();
        this.tablesId = other.tablesId == null ? null : other.tablesId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public KanbanTableCriteria copy() {
        return new KanbanTableCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public InstantFilter getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(InstantFilter modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LongFilter getTablesId() {
        return tablesId;
    }

    public void setTablesId(LongFilter tablesId) {
        this.tablesId = tablesId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KanbanTableCriteria that = (KanbanTableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(modificationDate, that.modificationDate) &&
            Objects.equals(tablesId, that.tablesId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        creationDate,
        modificationDate,
        tablesId,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KanbanTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (modificationDate != null ? "modificationDate=" + modificationDate + ", " : "") +
                (tablesId != null ? "tablesId=" + tablesId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
