package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.KanbanTableService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.KanbanTableDTO;
import com.mycompany.myapp.service.dto.KanbanTableCriteria;
import com.mycompany.myapp.service.KanbanTableQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.KanbanTable}.
 */
@RestController
@RequestMapping("/api")
public class KanbanTableResource {

    private final Logger log = LoggerFactory.getLogger(KanbanTableResource.class);

    private static final String ENTITY_NAME = "kanbanTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KanbanTableService kanbanTableService;

    private final KanbanTableQueryService kanbanTableQueryService;

    public KanbanTableResource(KanbanTableService kanbanTableService, KanbanTableQueryService kanbanTableQueryService) {
        this.kanbanTableService = kanbanTableService;
        this.kanbanTableQueryService = kanbanTableQueryService;
    }

    /**
     * {@code POST  /kanban-tables} : Create a new kanbanTable.
     *
     * @param kanbanTableDTO the kanbanTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kanbanTableDTO, or with status {@code 400 (Bad Request)} if the kanbanTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kanban-tables")
    public ResponseEntity<KanbanTableDTO> createKanbanTable(@Valid @RequestBody KanbanTableDTO kanbanTableDTO) throws URISyntaxException {
        log.debug("REST request to save KanbanTable : {}", kanbanTableDTO);
        if (kanbanTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new kanbanTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KanbanTableDTO result = kanbanTableService.save(kanbanTableDTO);
        return ResponseEntity.created(new URI("/api/kanban-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kanban-tables} : Updates an existing kanbanTable.
     *
     * @param kanbanTableDTO the kanbanTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kanbanTableDTO,
     * or with status {@code 400 (Bad Request)} if the kanbanTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kanbanTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kanban-tables")
    public ResponseEntity<KanbanTableDTO> updateKanbanTable(@Valid @RequestBody KanbanTableDTO kanbanTableDTO) throws URISyntaxException {
        log.debug("REST request to update KanbanTable : {}", kanbanTableDTO);
        if (kanbanTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KanbanTableDTO result = kanbanTableService.save(kanbanTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kanbanTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kanban-tables} : get all the kanbanTables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kanbanTables in body.
     */
    @GetMapping("/kanban-tables")
    public ResponseEntity<List<KanbanTableDTO>> getAllKanbanTables(KanbanTableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get KanbanTables by criteria: {}", criteria);
        Page<KanbanTableDTO> page = kanbanTableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kanban-tables} : get all the kanbanTables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kanbanTables in body.
     */
    @GetMapping("/kanban-tables-by-user-is-current-user")
    public ResponseEntity<List<KanbanTableDTO>> getAllKanbanTablesByUserIsCurrentUser(KanbanTableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get KanbanTables by criteria: {}", criteria);
        Page<KanbanTableDTO> page = kanbanTableQueryService.findByUserIsCurrentUserByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kanban-tables/count} : count all the kanbanTables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kanban-tables/count")
    public ResponseEntity<Long> countKanbanTables(KanbanTableCriteria criteria) {
        log.debug("REST request to count KanbanTables by criteria: {}", criteria);
        return ResponseEntity.ok().body(kanbanTableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /kanban-tables/:id} : get the "id" kanbanTable.
     *
     * @param id the id of the kanbanTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kanbanTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kanban-tables/{id}")
    public ResponseEntity<KanbanTableDTO> getKanbanTable(@PathVariable Long id) {
        log.debug("REST request to get KanbanTable : {}", id);
        Optional<KanbanTableDTO> kanbanTableDTO = kanbanTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kanbanTableDTO);
    }

    /**
     * {@code DELETE  /kanban-tables/:id} : delete the "id" kanbanTable.
     *
     * @param id the id of the kanbanTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kanban-tables/{id}")
    public ResponseEntity<Void> deleteKanbanTable(@PathVariable Long id) {
        log.debug("REST request to delete KanbanTable : {}", id);
        kanbanTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
