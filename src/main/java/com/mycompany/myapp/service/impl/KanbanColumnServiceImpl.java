package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.KanbanColumnService;
import com.mycompany.myapp.domain.KanbanColumn;
import com.mycompany.myapp.repository.KanbanColumnRepository;
import com.mycompany.myapp.service.dto.KanbanColumnDTO;
import com.mycompany.myapp.service.mapper.KanbanColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link KanbanColumn}.
 */
@Service
@Transactional
public class KanbanColumnServiceImpl implements KanbanColumnService {

    private final Logger log = LoggerFactory.getLogger(KanbanColumnServiceImpl.class);

    private final KanbanColumnRepository kanbanColumnRepository;

    private final KanbanColumnMapper kanbanColumnMapper;

    public KanbanColumnServiceImpl(KanbanColumnRepository kanbanColumnRepository, KanbanColumnMapper kanbanColumnMapper) {
        this.kanbanColumnRepository = kanbanColumnRepository;
        this.kanbanColumnMapper = kanbanColumnMapper;
    }

    /**
     * Save a kanbanColumn.
     *
     * @param kanbanColumnDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public KanbanColumnDTO save(KanbanColumnDTO kanbanColumnDTO) {
        log.debug("Request to save KanbanColumn : {}", kanbanColumnDTO);
        KanbanColumn kanbanColumn = kanbanColumnMapper.toEntity(kanbanColumnDTO);
        kanbanColumn = kanbanColumnRepository.save(kanbanColumn);
        return kanbanColumnMapper.toDto(kanbanColumn);
    }

    /**
     * Get all the kanbanColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KanbanColumnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KanbanColumns");
        return kanbanColumnRepository.findAll(pageable)
            .map(kanbanColumnMapper::toDto);
    }


    /**
     * Get one kanbanColumn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KanbanColumnDTO> findOne(Long id) {
        log.debug("Request to get KanbanColumn : {}", id);
        return kanbanColumnRepository.findById(id)
            .map(kanbanColumnMapper::toDto);
    }

    /**
     * Delete the kanbanColumn by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KanbanColumn : {}", id);
        kanbanColumnRepository.deleteById(id);
    }
}
