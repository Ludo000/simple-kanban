package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TaskCardImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskCardImage} and its DTO {@link TaskCardImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskCardMapper.class})
public interface TaskCardImageMapper extends EntityMapper<TaskCardImageDTO, TaskCardImage> {

    @Mapping(source = "taskCard.id", target = "taskCardId")
    TaskCardImageDTO toDto(TaskCardImage taskCardImage);

    @Mapping(source = "taskCardId", target = "taskCard")
    TaskCardImage toEntity(TaskCardImageDTO taskCardImageDTO);

    default TaskCardImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskCardImage taskCardImage = new TaskCardImage();
        taskCardImage.setId(id);
        return taskCardImage;
    }
}
