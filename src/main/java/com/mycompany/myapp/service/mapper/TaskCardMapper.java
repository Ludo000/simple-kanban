package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TaskCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskCard} and its DTO {@link TaskCardDTO}.
 */
@Mapper(componentModel = "spring", uses = {KanbanColumnMapper.class})
public interface TaskCardMapper extends EntityMapper<TaskCardDTO, TaskCard> {

    @Mapping(source = "kanbanColumn.id", target = "kanbanColumnId")
    TaskCardDTO toDto(TaskCard taskCard);

    @Mapping(target = "types", ignore = true)
    @Mapping(target = "removeTypes", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComments", ignore = true)
    @Mapping(source = "kanbanColumnId", target = "kanbanColumn")
    TaskCard toEntity(TaskCardDTO taskCardDTO);

    default TaskCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskCard taskCard = new TaskCard();
        taskCard.setId(id);
        return taskCard;
    }
}
