package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.KanbanTableService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.domain.KanbanTable;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.KanbanTableRepository;
import com.mycompany.myapp.service.dto.KanbanTableDTO;
import com.mycompany.myapp.service.mapper.KanbanTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
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

    private final UserService userService;

    public KanbanTableServiceImpl(KanbanTableRepository kanbanTableRepository, KanbanTableMapper kanbanTableMapper,
            UserService userService) {
        this.kanbanTableRepository = kanbanTableRepository;
        this.kanbanTableMapper = kanbanTableMapper;
        this.userService = userService;
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
        final Optional<User> isUser = userService.getUserWithAuthorities();
        if (isUser.isPresent()) {
            kanbanTable.setUser(isUser.get());
        }
        kanbanTable.setModificationDate(Instant.now());
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
        return kanbanTableRepository.findAll(pageable).map(kanbanTableMapper::toDto);
    }

    /**
     * Get all the kanbanTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KanbanTableDTO> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all KanbanTables");
        List<KanbanTable> kanbanTableList = kanbanTableRepository.findByUserIsCurrentUser();
        int start = Math.toIntExact(pageable.getOffset());
        int end = (start + pageable.getPageSize()) > kanbanTableList.size() ? kanbanTableList.size()
                : (start + pageable.getPageSize());
        Page<KanbanTable> pages = new PageImpl<KanbanTable>(kanbanTableList.subList(start, end), pageable,
                kanbanTableList.size());
        return pages.map(kanbanTableMapper::toDto);
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
        return kanbanTableRepository.findById(id).map(kanbanTableMapper::toDto);
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
