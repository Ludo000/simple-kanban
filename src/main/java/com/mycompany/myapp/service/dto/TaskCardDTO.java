package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TaskCard} entity.
 */
public class TaskCardDTO implements Serializable {
    
    private Long id;

    private String name;

    private String description;

    private String colorHexCode;

    @NotNull
    private Instant creationDate;

    @NotNull
    private Instant modificationDate;

    private Instant limitDate;


    private Long kanbanColumnId;
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColorHexCode() {
        return colorHexCode;
    }

    public void setColorHexCode(String colorHexCode) {
        this.colorHexCode = colorHexCode;
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

    public Instant getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Instant limitDate) {
        this.limitDate = limitDate;
    }

    public Long getKanbanColumnId() {
        return kanbanColumnId;
    }

    public void setKanbanColumnId(Long kanbanColumnId) {
        this.kanbanColumnId = kanbanColumnId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskCardDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskCardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCardDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", colorHexCode='" + getColorHexCode() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", limitDate='" + getLimitDate() + "'" +
            ", kanbanColumnId=" + getKanbanColumnId() +
            "}";
    }
}
