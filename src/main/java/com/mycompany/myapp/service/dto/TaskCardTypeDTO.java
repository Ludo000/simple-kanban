package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TaskCardType} entity.
 */
public class TaskCardTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;


    private Long taskCardId;
    
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

    public Long getTaskCardId() {
        return taskCardId;
    }

    public void setTaskCardId(Long taskCardId) {
        this.taskCardId = taskCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskCardTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskCardTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCardTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", taskCardId=" + getTaskCardId() +
            "}";
    }
}
