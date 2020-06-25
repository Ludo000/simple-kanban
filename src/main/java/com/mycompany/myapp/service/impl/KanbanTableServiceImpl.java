package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.KanbanTableService;
import com.mycompany.myapp.domain.KanbanTable;
import com.mycompany.myapp.repository.KanbanTableRepository;
import com.mycompany.myapp.service.dto.KanbanTableDTO;
import com.mycompany.myapp.service.mapper.KanbanTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link KanbanTable}.
 */
@Service
@Transactional
public class KanbanTableServiceImpl implements KanbanTableService {

    private final Logger log = LoggerFactory.getLogger(KanbanTableServiceImpl.class);

    private final KanbanTableRepository kanbanTableRepository;

    private final KanbanTableMapper kanbanTableMapper;

    public KanbanTableServiceImpl(KanbanTableRepository kanbanTableRepository, KanbanTableMapper kanbanTableMapper) {
        this.kanbanTableRepository = kanbanTableRepository;
        this.kanbanTableMapper = kanbanTableMapper;
    }

    /**
     * Save a kanbanTable.
     *
     * @param kanbanTableDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public KanbanTableDTO save(KanbanTableDTO kanbanTableDTO) {
        log.debug("Request to save KanbanTable : {}", kanbanTableDTO);
        KanbanTable kanbanTable = kanbanTableMapper.toEntity(kanbanTableDTO);
        kanbanTable = kanbanTableRepository.save(kanbanTable);
        return kanbanTableMapper.toDto(kanbanTable);
    }

    /**
     * Get all the kanbanTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KanbanTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KanbanTables");
        return kanbanTableRepository.findAll(pageable)
            .map(kanbanTableMapper::toDto);
    }


    /**
     * Get one kanbanTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KanbanTableDTO> findOne(Long id) {
        log.debug("Request to get KanbanTable : {}", id);
        return kanbanTableRepository.findById(id)
            .map(kanbanTableMapper::toDto);
    }

    /**
     * Delete the kanbanTable by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KanbanTable : {}", id);
        kanbanTableRepository.deleteById(id);
    }
}
