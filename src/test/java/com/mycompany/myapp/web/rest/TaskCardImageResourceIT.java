package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimpleKanbanApp;
import com.mycompany.myapp.domain.TaskCardImage;
import com.mycompany.myapp.repository.TaskCardImageRepository;
import com.mycompany.myapp.service.TaskCardImageService;
import com.mycompany.myapp.service.dto.TaskCardImageDTO;
import com.mycompany.myapp.service.mapper.TaskCardImageMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaskCardImageResource} REST controller.
 */
@SpringBootTest(classes = SimpleKanbanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskCardImageResourceIT {

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskCardImageRepository taskCardImageRepository;

    @Autowired
    private TaskCardImageMapper taskCardImageMapper;

    @Autowired
    private TaskCardImageService taskCardImageService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskCardImageMockMvc;

    private TaskCardImage taskCardImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCardImage createEntity(EntityManager em) {
        TaskCardImage taskCardImage = new TaskCardImage()
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE);
        return taskCardImage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCardImage createUpdatedEntity(EntityManager em) {
        TaskCardImage taskCardImage = new TaskCardImage()
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        return taskCardImage;
    }

    @BeforeEach
    public void initTest() {
        taskCardImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskCardImage() throws Exception {
        int databaseSizeBeforeCreate = taskCardImageRepository.findAll().size();
        // Create the TaskCardImage
        TaskCardImageDTO taskCardImageDTO = taskCardImageMapper.toDto(taskCardImage);
        restTaskCardImageMockMvc.perform(post("/api/task-card-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardImageDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskCardImage in the database
        List<TaskCardImage> taskCardImageList = taskCardImageRepository.findAll();
        assertThat(taskCardImageList).hasSize(databaseSizeBeforeCreate + 1);
        TaskCardImage testTaskCardImage = taskCardImageList.get(taskCardImageList.size() - 1);
        assertThat(testTaskCardImage.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTaskCardImage.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
        assertThat(testTaskCardImage.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTaskCardImage.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void createTaskCardImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskCardImageRepository.findAll().size();

        // Create the TaskCardImage with an existing ID
        taskCardImage.setId(1L);
        TaskCardImageDTO taskCardImageDTO = taskCardImageMapper.toDto(taskCardImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskCardImageMockMvc.perform(post("/api/task-card-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCardImage in the database
        List<TaskCardImage> taskCardImageList = taskCardImageRepository.findAll();
        assertThat(taskCardImageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCardImageRepository.findAll().size();
        // set the field null
        taskCardImage.setCreationDate(null);

        // Create the TaskCardImage, which fails.
        TaskCardImageDTO taskCardImageDTO = taskCardImageMapper.toDto(taskCardImage);


        restTaskCardImageMockMvc.perform(post("/api/task-card-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardImageDTO)))
            .andExpect(status().isBadRequest());

        List<TaskCardImage> taskCardImageList = taskCardImageRepository.findAll();
        assertThat(taskCardImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCardImageRepository.findAll().size();
        // set the field null
        taskCardImage.setModificationDate(null);

        // Create the TaskCardImage, which fails.
        TaskCardImageDTO taskCardImageDTO = taskCardImageMapper.toDto(taskCardImage);


        restTaskCardImageMockMvc.perform(post("/api/task-card-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardImageDTO)))
            .andExpect(status().isBadRequest());

        List<TaskCardImage> taskCardImageList = taskCardImageRepository.findAll();
        assertThat(taskCardImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskCardImages() throws Exception {
        // Initialize the database
        taskCardImageRepository.saveAndFlush(taskCardImage);

        // Get all the taskCardImageList
        restTaskCardImageMockMvc.perform(get("/api/task-card-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskCardImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTaskCardImage() throws Exception {
        // Initialize the database
        taskCardImageRepository.saveAndFlush(taskCardImage);

        // Get the taskCardImage
        restTaskCardImageMockMvc.perform(get("/api/task-card-images/{id}", taskCardImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskCardImage.getId().intValue()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaskCardImage() throws Exception {
        // Get the taskCardImage
        restTaskCardImageMockMvc.perform(get("/api/task-card-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskCardImage() throws Exception {
        // Initialize the database
        taskCardImageRepository.saveAndFlush(taskCardImage);

        int databaseSizeBeforeUpdate = taskCardImageRepository.findAll().size();

        // Update the taskCardImage
        TaskCardImage updatedTaskCardImage = taskCardImageRepository.findById(taskCardImage.getId()).get();
        // Disconnect from session so that the updates on updatedTaskCardImage are not directly saved in db
        em.detach(updatedTaskCardImage);
        updatedTaskCardImage
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        TaskCardImageDTO taskCardImageDTO = taskCardImageMapper.toDto(updatedTaskCardImage);

        restTaskCardImageMockMvc.perform(put("/api/task-card-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardImageDTO)))
            .andExpect(status().isOk());

        // Validate the TaskCardImage in the database
        List<TaskCardImage> taskCardImageList = taskCardImageRepository.findAll();
        assertThat(taskCardImageList).hasSize(databaseSizeBeforeUpdate);
        TaskCardImage testTaskCardImage = taskCardImageList.get(taskCardImageList.size() - 1);
        assertThat(testTaskCardImage.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTaskCardImage.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
        assertThat(testTaskCardImage.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTaskCardImage.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskCardImage() throws Exception {
        int databaseSizeBeforeUpdate = taskCardImageRepository.findAll().size();

        // Create the TaskCardImage
        TaskCardImageDTO taskCardImageDTO = taskCardImageMapper.toDto(taskCardImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskCardImageMockMvc.perform(put("/api/task-card-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCardImage in the database
        List<TaskCardImage> taskCardImageList = taskCardImageRepository.findAll();
        assertThat(taskCardImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskCardImage() throws Exception {
        // Initialize the database
        taskCardImageRepository.saveAndFlush(taskCardImage);

        int databaseSizeBeforeDelete = taskCardImageRepository.findAll().size();

        // Delete the taskCardImage
        restTaskCardImageMockMvc.perform(delete("/api/task-card-images/{id}", taskCardImage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskCardImage> taskCardImageList = taskCardImageRepository.findAll();
        assertThat(taskCardImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
