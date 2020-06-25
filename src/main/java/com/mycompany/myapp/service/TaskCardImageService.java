package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TaskCardImageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TaskCardImage}.
 */
public interface TaskCardImageService {

    /**
     * Save a taskCardImage.
     *
     * @param taskCardImageDTO the entity to save.
     * @return the persisted entity.
     */
    TaskCardImageDTO save(TaskCardImageDTO taskCardImageDTO);

    /**
     * Get all the taskCardImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskCardImageDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskCardImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskCardImageDTO> findOne(Long id);

    /**
     * Delete the "id" taskCardImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
