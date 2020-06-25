package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.KanbanTable;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.KanbanTableRepository;
import com.mycompany.myapp.service.dto.KanbanTableCriteria;
import com.mycompany.myapp.service.dto.KanbanTableDTO;
import com.mycompany.myapp.service.mapper.KanbanTableMapper;

/**
 * Service for executing complex queries for {@link KanbanTable} entities in the database.
 * The main input is a {@link KanbanTableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KanbanTableDTO} or a {@link Page} of {@link KanbanTableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KanbanTableQueryService extends QueryService<KanbanTable> {

    private final Logger log = LoggerFactory.getLogger(KanbanTableQueryService.class);

    private final KanbanTableRepository kanbanTableRepository;

    private final KanbanTableMapper kanbanTableMapper;

    public KanbanTableQueryService(KanbanTableRepository kanbanTableRepository, KanbanTableMapper kanbanTableMapper) {
        this.kanbanTableRepository = kanbanTableRepository;
        this.kanbanTableMapper = kanbanTableMapper;
    }

    /**
     * Return a {@link List} of {@link KanbanTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KanbanTableDTO> findByCriteria(KanbanTableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KanbanTable> specification = createSpecification(criteria);
        return kanbanTableMapper.toDto(kanbanTableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link KanbanTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KanbanTableDTO> findByCriteria(KanbanTableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KanbanTable> specification = createSpecification(criteria);
        return kanbanTableRepository.findAll(specification, page)
            .map(kanbanTableMapper::toDto);
    }

    /**
     * Return a {@link Page} of {@link KanbanTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KanbanTableDTO> findByUserIsCurrentUserByCriteria(KanbanTableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KanbanTable> specification = createSpecification(criteria);
        // TODO implement specification query
        List<KanbanTable> kanbanTableList = kanbanTableRepository.findByUserIsCurrentUser();
        int start = Math.toIntExact(page.getOffset());
        int end = (start + page.getPageSize()) > kanbanTableList.size() ? kanbanTableList.size() : (start + page.getPageSize());
        Page<KanbanTable> pages = new PageImpl<KanbanTable>(kanbanTableList.subList(start, end), page, kanbanTableList.size());
        
        return pages.map(kanbanTableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KanbanTableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KanbanTable> specification = createSpecification(criteria);
        return kanbanTableRepository.count(specification);
    }

    /**
     * Function to convert {@link KanbanTableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KanbanTable> createSpecification(KanbanTableCriteria criteria) {
        Specification<KanbanTable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KanbanTable_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), KanbanTable_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), KanbanTable_.description));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), KanbanTable_.creationDate));
            }
            if (criteria.getModificationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModificationDate(), KanbanTable_.modificationDate));
            }
            if (criteria.getTablesId() != null) {
                specification = specification.and(buildSpecification(criteria.getTablesId(),
                    root -> root.join(KanbanTable_.tables, JoinType.LEFT).get(KanbanColumn_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(KanbanTable_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
