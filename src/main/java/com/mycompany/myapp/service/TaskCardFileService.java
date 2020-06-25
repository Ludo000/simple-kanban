package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TaskCardFileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TaskCardFile}.
 */
public interface TaskCardFileService {

    /**
     * Save a taskCardFile.
     *
     * @param taskCardFileDTO the entity to save.
     * @return the persisted entity.
     */
    TaskCardFileDTO save(TaskCardFileDTO taskCardFileDTO);

    /**
     * Get all the taskCardFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskCardFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskCardFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskCardFileDTO> findOne(Long id);

    /**
     * Delete the "id" taskCardFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
