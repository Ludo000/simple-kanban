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
 * A TaskCard.
 */
@Entity
@Table(name = "task_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color_hex_code")
    private String colorHexCode;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @Column(name = "limit_date")
    private Instant limitDate;

    @OneToMany(mappedBy = "taskCard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TaskCardType> types = new HashSet<>();

    @OneToMany(mappedBy = "taskCard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TaskCardImage> images = new HashSet<>();

    @OneToMany(mappedBy = "taskCard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TaskCardFile> files = new HashSet<>();

    @OneToMany(mappedBy = "taskCard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "columns", allowSetters = true)
    private KanbanColumn kanbanColumn;

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

    public TaskCard name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TaskCard description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColorHexCode() {
        return colorHexCode;
    }

    public TaskCard colorHexCode(String colorHexCode) {
        this.colorHexCode = colorHexCode;
        return this;
    }

    public void setColorHexCode(String colorHexCode) {
        this.colorHexCode = colorHexCode;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public TaskCard creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public TaskCard modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Instant getLimitDate() {
        return limitDate;
    }

    public TaskCard limitDate(Instant limitDate) {
        this.limitDate = limitDate;
        return this;
    }

    public void setLimitDate(Instant limitDate) {
        this.limitDate = limitDate;
    }

    public Set<TaskCardType> getTypes() {
        return types;
    }

    public TaskCard types(Set<TaskCardType> taskCardTypes) {
        this.types = taskCardTypes;
        return this;
    }

    public TaskCard addTypes(TaskCardType taskCardType) {
        this.types.add(taskCardType);
        taskCardType.setTaskCard(this);
        return this;
    }

    public TaskCard removeTypes(TaskCardType taskCardType) {
        this.types.remove(taskCardType);
        taskCardType.setTaskCard(null);
        return this;
    }

    public void setTypes(Set<TaskCardType> taskCardTypes) {
        this.types = taskCardTypes;
    }

    public Set<TaskCardImage> getImages() {
        return images;
    }

    public TaskCard images(Set<TaskCardImage> taskCardImages) {
        this.images = taskCardImages;
        return this;
    }

    public TaskCard addImages(TaskCardImage taskCardImage) {
        this.images.add(taskCardImage);
        taskCardImage.setTaskCard(this);
        return this;
    }

    public TaskCard removeImages(TaskCardImage taskCardImage) {
        this.images.remove(taskCardImage);
        taskCardImage.setTaskCard(null);
        return this;
    }

    public void setImages(Set<TaskCardImage> taskCardImages) {
        this.images = taskCardImages;
    }

    public Set<TaskCardFile> getFiles() {
        return files;
    }

    public TaskCard files(Set<TaskCardFile> taskCardFiles) {
        this.files = taskCardFiles;
        return this;
    }

    public TaskCard addFiles(TaskCardFile taskCardFile) {
        this.files.add(taskCardFile);
        taskCardFile.setTaskCard(this);
        return this;
    }

    public TaskCard removeFiles(TaskCardFile taskCardFile) {
        this.files.remove(taskCardFile);
        taskCardFile.setTaskCard(null);
        return this;
    }

    public void setFiles(Set<TaskCardFile> taskCardFiles) {
        this.files = taskCardFiles;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public TaskCard comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public TaskCard addComments(Comment comment) {
        this.comments.add(comment);
        comment.setTaskCard(this);
        return this;
    }

    public TaskCard removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setTaskCard(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public KanbanColumn getKanbanColumn() {
        return kanbanColumn;
    }

    public TaskCard kanbanColumn(KanbanColumn kanbanColumn) {
        this.kanbanColumn = kanbanColumn;
        return this;
    }

    public void setKanbanColumn(KanbanColumn kanbanColumn) {
        this.kanbanColumn = kanbanColumn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskCard)) {
            return false;
        }
        return id != null && id.equals(((TaskCard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCard{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", colorHexCode='" + getColorHexCode() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", limitDate='" + getLimitDate() + "'" +
            "}";
    }
}
