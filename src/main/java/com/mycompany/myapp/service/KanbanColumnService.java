package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.KanbanColumnDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.KanbanColumn}.
 */
public interface KanbanColumnService {

    /**
     * Save a kanbanColumn.
     *
     * @param kanbanColumnDTO the entity to save.
     * @return the persisted entity.
     */
    KanbanColumnDTO save(KanbanColumnDTO kanbanColumnDTO);

    /**
     * Get all the kanbanColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KanbanColumnDTO> findAll(Pageable pageable);


    /**
     * Get the "id" kanbanColumn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KanbanColumnDTO> findOne(Long id);

    /**
     * Delete the "id" kanbanColumn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
