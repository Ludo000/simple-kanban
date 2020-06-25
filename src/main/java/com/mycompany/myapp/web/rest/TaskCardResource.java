package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TaskCardService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.TaskCardDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.TaskCard}.
 */
@RestController
@RequestMapping("/api")
public class TaskCardResource {

    private final Logger log = LoggerFactory.getLogger(TaskCardResource.class);

    private static final String ENTITY_NAME = "taskCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskCardService taskCardService;

    public TaskCardResource(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    /**
     * {@code POST  /task-cards} : Create a new taskCard.
     *
     * @param taskCardDTO the taskCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskCardDTO, or with status {@code 400 (Bad Request)} if the taskCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-cards")
    public ResponseEntity<TaskCardDTO> createTaskCard(@Valid @RequestBody TaskCardDTO taskCardDTO) throws URISyntaxException {
        log.debug("REST request to save TaskCard : {}", taskCardDTO);
        if (taskCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskCardDTO result = taskCardService.save(taskCardDTO);
        return ResponseEntity.created(new URI("/api/task-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-cards} : Updates an existing taskCard.
     *
     * @param taskCardDTO the taskCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskCardDTO,
     * or with status {@code 400 (Bad Request)} if the taskCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-cards")
    public ResponseEntity<TaskCardDTO> updateTaskCard(@Valid @RequestBody TaskCardDTO taskCardDTO) throws URISyntaxException {
        log.debug("REST request to update TaskCard : {}", taskCardDTO);
        if (taskCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskCardDTO result = taskCardService.save(taskCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-cards} : get all the taskCards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskCards in body.
     */
    @GetMapping("/task-cards")
    public ResponseEntity<List<TaskCardDTO>> getAllTaskCards(Pageable pageable) {
        log.debug("REST request to get a page of TaskCards");
        Page<TaskCardDTO> page = taskCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-cards/:id} : get the "id" taskCard.
     *
     * @param id the id of the taskCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-cards/{id}")
    public ResponseEntity<TaskCardDTO> getTaskCard(@PathVariable Long id) {
        log.debug("REST request to get TaskCard : {}", id);
        Optional<TaskCardDTO> taskCardDTO = taskCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskCardDTO);
    }

    /**
     * {@code DELETE  /task-cards/:id} : delete the "id" taskCard.
     *
     * @param id the id of the taskCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-cards/{id}")
    public ResponseEntity<Void> deleteTaskCard(@PathVariable Long id) {
        log.debug("REST request to delete TaskCard : {}", id);
        taskCardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
