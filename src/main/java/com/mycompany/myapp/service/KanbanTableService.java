package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.KanbanTableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.KanbanTable}.
 */
public interface KanbanTableService {

    /**
     * Save a kanbanTable.
     *
     * @param kanbanTableDTO the entity to save.
     * @return the persisted entity.
     */
    KanbanTableDTO save(KanbanTableDTO kanbanTableDTO);

    /**
     * Get all the kanbanTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KanbanTableDTO> findAll(Pageable pageable);
    
    /**
     * Get all the kanbanTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<KanbanTableDTO> findByUserIsCurrentUser(Pageable pageable);

    /**
     * Get the "id" kanbanTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KanbanTableDTO> findOne(Long id);

    /**
     * Delete the "id" kanbanTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
