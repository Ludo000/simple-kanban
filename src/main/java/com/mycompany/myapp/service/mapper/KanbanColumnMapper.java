package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.KanbanColumnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link KanbanColumn} and its DTO {@link KanbanColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = {KanbanTableMapper.class})
public interface KanbanColumnMapper extends EntityMapper<KanbanColumnDTO, KanbanColumn> {

    @Mapping(source = "kanbanTable.id", target = "kanbanTableId")
    KanbanColumnDTO toDto(KanbanColumn kanbanColumn);

    @Mapping(target = "columns", ignore = true)
    @Mapping(target = "removeColumns", ignore = true)
    @Mapping(source = "kanbanTableId", target = "kanbanTable")
    KanbanColumn toEntity(KanbanColumnDTO kanbanColumnDTO);

    default KanbanColumn fromId(Long id) {
        if (id == null) {
            return null;
        }
        KanbanColumn kanbanColumn = new KanbanColumn();
        kanbanColumn.setId(id);
        return kanbanColumn;
    }
}
