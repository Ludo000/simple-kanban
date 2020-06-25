package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TaskCardFileService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.TaskCardFileDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.TaskCardFile}.
 */
@RestController
@RequestMapping("/api")
public class TaskCardFileResource {

    private final Logger log = LoggerFactory.getLogger(TaskCardFileResource.class);

    private static final String ENTITY_NAME = "taskCardFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskCardFileService taskCardFileService;

    public TaskCardFileResource(TaskCardFileService taskCardFileService) {
        this.taskCardFileService = taskCardFileService;
    }

    /**
     * {@code POST  /task-card-files} : Create a new taskCardFile.
     *
     * @param taskCardFileDTO the taskCardFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskCardFileDTO, or with status {@code 400 (Bad Request)} if the taskCardFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-card-files")
    public ResponseEntity<TaskCardFileDTO> createTaskCardFile(@Valid @RequestBody TaskCardFileDTO taskCardFileDTO) throws URISyntaxException {
        log.debug("REST request to save TaskCardFile : {}", taskCardFileDTO);
        if (taskCardFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskCardFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskCardFileDTO result = taskCardFileService.save(taskCardFileDTO);
        return ResponseEntity.created(new URI("/api/task-card-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-card-files} : Updates an existing taskCardFile.
     *
     * @param taskCardFileDTO the taskCardFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskCardFileDTO,
     * or with status {@code 400 (Bad Request)} if the taskCardFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskCardFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-card-files")
    public ResponseEntity<TaskCardFileDTO> updateTaskCardFile(@Valid @RequestBody TaskCardFileDTO taskCardFileDTO) throws URISyntaxException {
        log.debug("REST request to update TaskCardFile : {}", taskCardFileDTO);
        if (taskCardFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskCardFileDTO result = taskCardFileService.save(taskCardFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskCardFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-card-files} : get all the taskCardFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskCardFiles in body.
     */
    @GetMapping("/task-card-files")
    public ResponseEntity<List<TaskCardFileDTO>> getAllTaskCardFiles(Pageable pageable) {
        log.debug("REST request to get a page of TaskCardFiles");
        Page<TaskCardFileDTO> page = taskCardFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-card-files/:id} : get the "id" taskCardFile.
     *
     * @param id the id of the taskCardFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskCardFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-card-files/{id}")
    public ResponseEntity<TaskCardFileDTO> getTaskCardFile(@PathVariable Long id) {
        log.debug("REST request to get TaskCardFile : {}", id);
        Optional<TaskCardFileDTO> taskCardFileDTO = taskCardFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskCardFileDTO);
    }

    /**
     * {@code DELETE  /task-card-files/:id} : delete the "id" taskCardFile.
     *
     * @param id the id of the taskCardFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-card-files/{id}")
    public ResponseEntity<Void> deleteTaskCardFile(@PathVariable Long id) {
        log.debug("REST request to delete TaskCardFile : {}", id);
        taskCardFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
