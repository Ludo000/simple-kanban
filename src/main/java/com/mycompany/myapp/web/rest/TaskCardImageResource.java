package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TaskCardImageService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.TaskCardImageDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.TaskCardImage}.
 */
@RestController
@RequestMapping("/api")
public class TaskCardImageResource {

    private final Logger log = LoggerFactory.getLogger(TaskCardImageResource.class);

    private static final String ENTITY_NAME = "taskCardImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskCardImageService taskCardImageService;

    public TaskCardImageResource(TaskCardImageService taskCardImageService) {
        this.taskCardImageService = taskCardImageService;
    }

    /**
     * {@code POST  /task-card-images} : Create a new taskCardImage.
     *
     * @param taskCardImageDTO the taskCardImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskCardImageDTO, or with status {@code 400 (Bad Request)} if the taskCardImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-card-images")
    public ResponseEntity<TaskCardImageDTO> createTaskCardImage(@Valid @RequestBody TaskCardImageDTO taskCardImageDTO) throws URISyntaxException {
        log.debug("REST request to save TaskCardImage : {}", taskCardImageDTO);
        if (taskCardImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskCardImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskCardImageDTO result = taskCardImageService.save(taskCardImageDTO);
        return ResponseEntity.created(new URI("/api/task-card-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-card-images} : Updates an existing taskCardImage.
     *
     * @param taskCardImageDTO the taskCardImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskCardImageDTO,
     * or with status {@code 400 (Bad Request)} if the taskCardImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskCardImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-card-images")
    public ResponseEntity<TaskCardImageDTO> updateTaskCardImage(@Valid @RequestBody TaskCardImageDTO taskCardImageDTO) throws URISyntaxException {
        log.debug("REST request to update TaskCardImage : {}", taskCardImageDTO);
        if (taskCardImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskCardImageDTO result = taskCardImageService.save(taskCardImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskCardImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-card-images} : get all the taskCardImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskCardImages in body.
     */
    @GetMapping("/task-card-images")
    public ResponseEntity<List<TaskCardImageDTO>> getAllTaskCardImages(Pageable pageable) {
        log.debug("REST request to get a page of TaskCardImages");
        Page<TaskCardImageDTO> page = taskCardImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-card-images/:id} : get the "id" taskCardImage.
     *
     * @param id the id of the taskCardImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskCardImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-card-images/{id}")
    public ResponseEntity<TaskCardImageDTO> getTaskCardImage(@PathVariable Long id) {
        log.debug("REST request to get TaskCardImage : {}", id);
        Optional<TaskCardImageDTO> taskCardImageDTO = taskCardImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskCardImageDTO);
    }

    /**
     * {@code DELETE  /task-card-images/:id} : delete the "id" taskCardImage.
     *
     * @param id the id of the taskCardImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-card-images/{id}")
    public ResponseEntity<Void> deleteTaskCardImage(@PathVariable Long id) {
        log.debug("REST request to delete TaskCardImage : {}", id);
        taskCardImageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
