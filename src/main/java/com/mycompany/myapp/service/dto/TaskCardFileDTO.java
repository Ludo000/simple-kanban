package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TaskCardFile} entity.
 */
public class TaskCardFileDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private byte[] data;

    private String dataContentType;
    @NotNull
    private Instant creationDate;

    @NotNull
    private Instant modificationDate;


    private Long taskCardId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
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
        if (!(o instanceof TaskCardFileDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskCardFileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCardFileDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", taskCardId=" + getTaskCardId() +
            "}";
    }
}
