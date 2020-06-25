package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TaskCardTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskCardType} and its DTO {@link TaskCardTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskCardMapper.class})
public interface TaskCardTypeMapper extends EntityMapper<TaskCardTypeDTO, TaskCardType> {

    @Mapping(source = "taskCard.id", target = "taskCardId")
    TaskCardTypeDTO toDto(TaskCardType taskCardType);

    @Mapping(source = "taskCardId", target = "taskCard")
    TaskCardType toEntity(TaskCardTypeDTO taskCardTypeDTO);

    default TaskCardType fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskCardType taskCardType = new TaskCardType();
        taskCardType.setId(id);
        return taskCardType;
    }
}
