package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TaskCardDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TaskCard}.
 */
public interface TaskCardService {

    /**
     * Save a taskCard.
     *
     * @param taskCardDTO the entity to save.
     * @return the persisted entity.
     */
    TaskCardDTO save(TaskCardDTO taskCardDTO);

    /**
     * Get all the taskCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskCardDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskCardDTO> findOne(Long id);

    /**
     * Delete the "id" taskCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
