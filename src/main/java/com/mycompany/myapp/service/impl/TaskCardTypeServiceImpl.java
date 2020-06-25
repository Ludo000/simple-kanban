package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TaskCardTypeService;
import com.mycompany.myapp.domain.TaskCardType;
import com.mycompany.myapp.repository.TaskCardTypeRepository;
import com.mycompany.myapp.service.dto.TaskCardTypeDTO;
import com.mycompany.myapp.service.mapper.TaskCardTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskCardType}.
 */
@Service
@Transactional
public class TaskCardTypeServiceImpl implements TaskCardTypeService {

    private final Logger log = LoggerFactory.getLogger(TaskCardTypeServiceImpl.class);

    private final TaskCardTypeRepository taskCardTypeRepository;

    private final TaskCardTypeMapper taskCardTypeMapper;

    public TaskCardTypeServiceImpl(TaskCardTypeRepository taskCardTypeRepository, TaskCardTypeMapper taskCardTypeMapper) {
        this.taskCardTypeRepository = taskCardTypeRepository;
        this.taskCardTypeMapper = taskCardTypeMapper;
    }

    /**
     * Save a taskCardType.
     *
     * @param taskCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskCardTypeDTO save(TaskCardTypeDTO taskCardTypeDTO) {
        log.debug("Request to save TaskCardType : {}", taskCardTypeDTO);
        TaskCardType taskCardType = taskCardTypeMapper.toEntity(taskCardTypeDTO);
        taskCardType = taskCardTypeRepository.save(taskCardType);
        return taskCardTypeMapper.toDto(taskCardType);
    }

    /**
     * Get all the taskCardTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskCardTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskCardTypes");
        return taskCardTypeRepository.findAll(pageable)
            .map(taskCardTypeMapper::toDto);
    }


    /**
     * Get one taskCardType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskCardTypeDTO> findOne(Long id) {
        log.debug("Request to get TaskCardType : {}", id);
        return taskCardTypeRepository.findById(id)
            .map(taskCardTypeMapper::toDto);
    }

    /**
     * Delete the taskCardType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskCardType : {}", id);
        taskCardTypeRepository.deleteById(id);
    }
}
