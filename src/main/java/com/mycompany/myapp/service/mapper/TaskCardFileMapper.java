package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TaskCardFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskCardFile} and its DTO {@link TaskCardFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskCardMapper.class})
public interface TaskCardFileMapper extends EntityMapper<TaskCardFileDTO, TaskCardFile> {

    @Mapping(source = "taskCard.id", target = "taskCardId")
    TaskCardFileDTO toDto(TaskCardFile taskCardFile);

    @Mapping(source = "taskCardId", target = "taskCard")
    TaskCardFile toEntity(TaskCardFileDTO taskCardFileDTO);

    default TaskCardFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskCardFile taskCardFile = new TaskCardFile();
        taskCardFile.setId(id);
        return taskCardFile;
    }
}
