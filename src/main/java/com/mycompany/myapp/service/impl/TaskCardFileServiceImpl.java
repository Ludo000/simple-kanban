package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TaskCardFileService;
import com.mycompany.myapp.domain.TaskCardFile;
import com.mycompany.myapp.repository.TaskCardFileRepository;
import com.mycompany.myapp.service.dto.TaskCardFileDTO;
import com.mycompany.myapp.service.mapper.TaskCardFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskCardFile}.
 */
@Service
@Transactional
public class TaskCardFileServiceImpl implements TaskCardFileService {

    private final Logger log = LoggerFactory.getLogger(TaskCardFileServiceImpl.class);

    private final TaskCardFileRepository taskCardFileRepository;

    private final TaskCardFileMapper taskCardFileMapper;

    public TaskCardFileServiceImpl(TaskCardFileRepository taskCardFileRepository, TaskCardFileMapper taskCardFileMapper) {
        this.taskCardFileRepository = taskCardFileRepository;
        this.taskCardFileMapper = taskCardFileMapper;
    }

    /**
     * Save a taskCardFile.
     *
     * @param taskCardFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskCardFileDTO save(TaskCardFileDTO taskCardFileDTO) {
        log.debug("Request to save TaskCardFile : {}", taskCardFileDTO);
        TaskCardFile taskCardFile = taskCardFileMapper.toEntity(taskCardFileDTO);
        taskCardFile = taskCardFileRepository.save(taskCardFile);
        return taskCardFileMapper.toDto(taskCardFile);
    }

    /**
     * Get all the taskCardFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskCardFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskCardFiles");
        return taskCardFileRepository.findAll(pageable)
            .map(taskCardFileMapper::toDto);
    }


    /**
     * Get one taskCardFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskCardFileDTO> findOne(Long id) {
        log.debug("Request to get TaskCardFile : {}", id);
        return taskCardFileRepository.findById(id)
            .map(taskCardFileMapper::toDto);
    }

    /**
     * Delete the taskCardFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskCardFile : {}", id);
        taskCardFileRepository.deleteById(id);
    }
}
