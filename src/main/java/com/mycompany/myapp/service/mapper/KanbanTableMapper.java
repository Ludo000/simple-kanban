package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.KanbanTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link KanbanTable} and its DTO {@link KanbanTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface KanbanTableMapper extends EntityMapper<KanbanTableDTO, KanbanTable> {

    @Mapping(source = "user.id", target = "userId")
    KanbanTableDTO toDto(KanbanTable kanbanTable);

    @Mapping(target = "tables", ignore = true)
    @Mapping(target = "removeTables", ignore = true)
    @Mapping(source = "userId", target = "user")
    KanbanTable toEntity(KanbanTableDTO kanbanTableDTO);

    default KanbanTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        KanbanTable kanbanTable = new KanbanTable();
        kanbanTable.setId(id);
        return kanbanTable;
    }
}
