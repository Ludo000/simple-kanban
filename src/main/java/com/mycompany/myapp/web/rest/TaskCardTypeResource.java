package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TaskCardTypeService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.TaskCardTypeDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.TaskCardType}.
 */
@RestController
@RequestMapping("/api")
public class TaskCardTypeResource {

    private final Logger log = LoggerFactory.getLogger(TaskCardTypeResource.class);

    private static final String ENTITY_NAME = "taskCardType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskCardTypeService taskCardTypeService;

    public TaskCardTypeResource(TaskCardTypeService taskCardTypeService) {
        this.taskCardTypeService = taskCardTypeService;
    }

    /**
     * {@code POST  /task-card-types} : Create a new taskCardType.
     *
     * @param taskCardTypeDTO the taskCardTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskCardTypeDTO, or with status {@code 400 (Bad Request)} if the taskCardType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-card-types")
    public ResponseEntity<TaskCardTypeDTO> createTaskCardType(@Valid @RequestBody TaskCardTypeDTO taskCardTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TaskCardType : {}", taskCardTypeDTO);
        if (taskCardTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskCardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskCardTypeDTO result = taskCardTypeService.save(taskCardTypeDTO);
        return ResponseEntity.created(new URI("/api/task-card-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-card-types} : Updates an existing taskCardType.
     *
     * @param taskCardTypeDTO the taskCardTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskCardTypeDTO,
     * or with status {@code 400 (Bad Request)} if the taskCardTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskCardTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-card-types")
    public ResponseEntity<TaskCardTypeDTO> updateTaskCardType(@Valid @RequestBody TaskCardTypeDTO taskCardTypeDTO) throws URISyntaxException {
        log.debug("REST request to update TaskCardType : {}", taskCardTypeDTO);
        if (taskCardTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskCardTypeDTO result = taskCardTypeService.save(taskCardTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskCardTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-card-types} : get all the taskCardTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskCardTypes in body.
     */
    @GetMapping("/task-card-types")
    public ResponseEntity<List<TaskCardTypeDTO>> getAllTaskCardTypes(Pageable pageable) {
        log.debug("REST request to get a page of TaskCardTypes");
        Page<TaskCardTypeDTO> page = taskCardTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-card-types/:id} : get the "id" taskCardType.
     *
     * @param id the id of the taskCardTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskCardTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-card-types/{id}")
    public ResponseEntity<TaskCardTypeDTO> getTaskCardType(@PathVariable Long id) {
        log.debug("REST request to get TaskCardType : {}", id);
        Optional<TaskCardTypeDTO> taskCardTypeDTO = taskCardTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskCardTypeDTO);
    }

    /**
     * {@code DELETE  /task-card-types/:id} : delete the "id" taskCardType.
     *
     * @param id the id of the taskCardTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-card-types/{id}")
    public ResponseEntity<Void> deleteTaskCardType(@PathVariable Long id) {
        log.debug("REST request to delete TaskCardType : {}", id);
        taskCardTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
