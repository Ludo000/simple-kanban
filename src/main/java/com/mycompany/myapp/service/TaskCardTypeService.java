package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TaskCardTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TaskCardType}.
 */
public interface TaskCardTypeService {

    /**
     * Save a taskCardType.
     *
     * @param taskCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    TaskCardTypeDTO save(TaskCardTypeDTO taskCardTypeDTO);

    /**
     * Get all the taskCardTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskCardTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskCardType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskCardTypeDTO> findOne(Long id);

    /**
     * Delete the "id" taskCardType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
