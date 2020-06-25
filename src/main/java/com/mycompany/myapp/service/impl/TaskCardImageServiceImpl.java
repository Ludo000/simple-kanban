package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TaskCardImageService;
import com.mycompany.myapp.domain.TaskCardImage;
import com.mycompany.myapp.repository.TaskCardImageRepository;
import com.mycompany.myapp.service.dto.TaskCardImageDTO;
import com.mycompany.myapp.service.mapper.TaskCardImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskCardImage}.
 */
@Service
@Transactional
public class TaskCardImageServiceImpl implements TaskCardImageService {

    private final Logger log = LoggerFactory.getLogger(TaskCardImageServiceImpl.class);

    private final TaskCardImageRepository taskCardImageRepository;

    private final TaskCardImageMapper taskCardImageMapper;

    public TaskCardImageServiceImpl(TaskCardImageRepository taskCardImageRepository, TaskCardImageMapper taskCardImageMapper) {
        this.taskCardImageRepository = taskCardImageRepository;
        this.taskCardImageMapper = taskCardImageMapper;
    }

    /**
     * Save a taskCardImage.
     *
     * @param taskCardImageDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskCardImageDTO save(TaskCardImageDTO taskCardImageDTO) {
        log.debug("Request to save TaskCardImage : {}", taskCardImageDTO);
        TaskCardImage taskCardImage = taskCardImageMapper.toEntity(taskCardImageDTO);
        taskCardImage = taskCardImageRepository.save(taskCardImage);
        return taskCardImageMapper.toDto(taskCardImage);
    }

    /**
     * Get all the taskCardImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskCardImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskCardImages");
        return taskCardImageRepository.findAll(pageable)
            .map(taskCardImageMapper::toDto);
    }


    /**
     * Get one taskCardImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskCardImageDTO> findOne(Long id) {
        log.debug("Request to get TaskCardImage : {}", id);
        return taskCardImageRepository.findById(id)
            .map(taskCardImageMapper::toDto);
    }

    /**
     * Delete the taskCardImage by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskCardImage : {}", id);
        taskCardImageRepository.deleteById(id);
    }
}
