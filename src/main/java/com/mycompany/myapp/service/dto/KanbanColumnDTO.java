package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.KanbanColumn} entity.
 */
public class KanbanColumnDTO implements Serializable {
    
    private Long id;

    private String name;

    @NotNull
    private Instant creationDate;

    @NotNull
    private Instant modificationDate;


    private Long kanbanTableId;
    
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

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getKanbanTableId() {
        return kanbanTableId;
    }

    public void setKanbanTableId(Long kanbanTableId) {
        this.kanbanTableId = kanbanTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KanbanColumnDTO)) {
            return false;
        }

        return id != null && id.equals(((KanbanColumnDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KanbanColumnDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", kanbanTableId=" + getKanbanTableId() +
            "}";
    }
}
