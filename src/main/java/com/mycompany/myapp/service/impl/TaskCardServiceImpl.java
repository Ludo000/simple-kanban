package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TaskCardService;
import com.mycompany.myapp.domain.TaskCard;
import com.mycompany.myapp.repository.TaskCardRepository;
import com.mycompany.myapp.service.dto.TaskCardDTO;
import com.mycompany.myapp.service.mapper.TaskCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskCard}.
 */
@Service
@Transactional
public class TaskCardServiceImpl implements TaskCardService {

    private final Logger log = LoggerFactory.getLogger(TaskCardServiceImpl.class);

    private final TaskCardRepository taskCardRepository;

    private final TaskCardMapper taskCardMapper;

    public TaskCardServiceImpl(TaskCardRepository taskCardRepository, TaskCardMapper taskCardMapper) {
        this.taskCardRepository = taskCardRepository;
        this.taskCardMapper = taskCardMapper;
    }

    /**
     * Save a taskCard.
     *
     * @param taskCardDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskCardDTO save(TaskCardDTO taskCardDTO) {
        log.debug("Request to save TaskCard : {}", taskCardDTO);
        TaskCard taskCard = taskCardMapper.toEntity(taskCardDTO);
        taskCard = taskCardRepository.save(taskCard);
        return taskCardMapper.toDto(taskCard);
    }

    /**
     * Get all the taskCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskCardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskCards");
        return taskCardRepository.findAll(pageable)
            .map(taskCardMapper::toDto);
    }


    /**
     * Get one taskCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskCardDTO> findOne(Long id) {
        log.debug("Request to get TaskCard : {}", id);
        return taskCardRepository.findById(id)
            .map(taskCardMapper::toDto);
    }

    /**
     * Delete the taskCard by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskCard : {}", id);
        taskCardRepository.deleteById(id);
    }
}
