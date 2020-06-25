package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimpleKanbanApp;
import com.mycompany.myapp.domain.TaskCardFile;
import com.mycompany.myapp.repository.TaskCardFileRepository;
import com.mycompany.myapp.service.TaskCardFileService;
import com.mycompany.myapp.service.dto.TaskCardFileDTO;
import com.mycompany.myapp.service.mapper.TaskCardFileMapper;

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
 * Integration tests for the {@link TaskCardFileResource} REST controller.
 */
@SpringBootTest(classes = SimpleKanbanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskCardFileResourceIT {

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskCardFileRepository taskCardFileRepository;

    @Autowired
    private TaskCardFileMapper taskCardFileMapper;

    @Autowired
    private TaskCardFileService taskCardFileService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskCardFileMockMvc;

    private TaskCardFile taskCardFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCardFile createEntity(EntityManager em) {
        TaskCardFile taskCardFile = new TaskCardFile()
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE);
        return taskCardFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCardFile createUpdatedEntity(EntityManager em) {
        TaskCardFile taskCardFile = new TaskCardFile()
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        return taskCardFile;
    }

    @BeforeEach
    public void initTest() {
        taskCardFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskCardFile() throws Exception {
        int databaseSizeBeforeCreate = taskCardFileRepository.findAll().size();
        // Create the TaskCardFile
        TaskCardFileDTO taskCardFileDTO = taskCardFileMapper.toDto(taskCardFile);
        restTaskCardFileMockMvc.perform(post("/api/task-card-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardFileDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskCardFile in the database
        List<TaskCardFile> taskCardFileList = taskCardFileRepository.findAll();
        assertThat(taskCardFileList).hasSize(databaseSizeBeforeCreate + 1);
        TaskCardFile testTaskCardFile = taskCardFileList.get(taskCardFileList.size() - 1);
        assertThat(testTaskCardFile.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTaskCardFile.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
        assertThat(testTaskCardFile.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTaskCardFile.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void createTaskCardFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskCardFileRepository.findAll().size();

        // Create the TaskCardFile with an existing ID
        taskCardFile.setId(1L);
        TaskCardFileDTO taskCardFileDTO = taskCardFileMapper.toDto(taskCardFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskCardFileMockMvc.perform(post("/api/task-card-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCardFile in the database
        List<TaskCardFile> taskCardFileList = taskCardFileRepository.findAll();
        assertThat(taskCardFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCardFileRepository.findAll().size();
        // set the field null
        taskCardFile.setCreationDate(null);

        // Create the TaskCardFile, which fails.
        TaskCardFileDTO taskCardFileDTO = taskCardFileMapper.toDto(taskCardFile);


        restTaskCardFileMockMvc.perform(post("/api/task-card-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardFileDTO)))
            .andExpect(status().isBadRequest());

        List<TaskCardFile> taskCardFileList = taskCardFileRepository.findAll();
        assertThat(taskCardFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCardFileRepository.findAll().size();
        // set the field null
        taskCardFile.setModificationDate(null);

        // Create the TaskCardFile, which fails.
        TaskCardFileDTO taskCardFileDTO = taskCardFileMapper.toDto(taskCardFile);


        restTaskCardFileMockMvc.perform(post("/api/task-card-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardFileDTO)))
            .andExpect(status().isBadRequest());

        List<TaskCardFile> taskCardFileList = taskCardFileRepository.findAll();
        assertThat(taskCardFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskCardFiles() throws Exception {
        // Initialize the database
        taskCardFileRepository.saveAndFlush(taskCardFile);

        // Get all the taskCardFileList
        restTaskCardFileMockMvc.perform(get("/api/task-card-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskCardFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTaskCardFile() throws Exception {
        // Initialize the database
        taskCardFileRepository.saveAndFlush(taskCardFile);

        // Get the taskCardFile
        restTaskCardFileMockMvc.perform(get("/api/task-card-files/{id}", taskCardFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskCardFile.getId().intValue()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaskCardFile() throws Exception {
        // Get the taskCardFile
        restTaskCardFileMockMvc.perform(get("/api/task-card-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskCardFile() throws Exception {
        // Initialize the database
        taskCardFileRepository.saveAndFlush(taskCardFile);

        int databaseSizeBeforeUpdate = taskCardFileRepository.findAll().size();

        // Update the taskCardFile
        TaskCardFile updatedTaskCardFile = taskCardFileRepository.findById(taskCardFile.getId()).get();
        // Disconnect from session so that the updates on updatedTaskCardFile are not directly saved in db
        em.detach(updatedTaskCardFile);
        updatedTaskCardFile
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        TaskCardFileDTO taskCardFileDTO = taskCardFileMapper.toDto(updatedTaskCardFile);

        restTaskCardFileMockMvc.perform(put("/api/task-card-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardFileDTO)))
            .andExpect(status().isOk());

        // Validate the TaskCardFile in the database
        List<TaskCardFile> taskCardFileList = taskCardFileRepository.findAll();
        assertThat(taskCardFileList).hasSize(databaseSizeBeforeUpdate);
        TaskCardFile testTaskCardFile = taskCardFileList.get(taskCardFileList.size() - 1);
        assertThat(testTaskCardFile.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTaskCardFile.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
        assertThat(testTaskCardFile.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTaskCardFile.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskCardFile() throws Exception {
        int databaseSizeBeforeUpdate = taskCardFileRepository.findAll().size();

        // Create the TaskCardFile
        TaskCardFileDTO taskCardFileDTO = taskCardFileMapper.toDto(taskCardFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskCardFileMockMvc.perform(put("/api/task-card-files")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCardFile in the database
        List<TaskCardFile> taskCardFileList = taskCardFileRepository.findAll();
        assertThat(taskCardFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskCardFile() throws Exception {
        // Initialize the database
        taskCardFileRepository.saveAndFlush(taskCardFile);

        int databaseSizeBeforeDelete = taskCardFileRepository.findAll().size();

        // Delete the taskCardFile
        restTaskCardFileMockMvc.perform(delete("/api/task-card-files/{id}", taskCardFile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskCardFile> taskCardFileList = taskCardFileRepository.findAll();
        assertThat(taskCardFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
