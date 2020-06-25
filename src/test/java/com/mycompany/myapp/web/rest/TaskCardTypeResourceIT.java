package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimpleKanbanApp;
import com.mycompany.myapp.domain.TaskCardType;
import com.mycompany.myapp.repository.TaskCardTypeRepository;
import com.mycompany.myapp.service.TaskCardTypeService;
import com.mycompany.myapp.service.dto.TaskCardTypeDTO;
import com.mycompany.myapp.service.mapper.TaskCardTypeMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaskCardTypeResource} REST controller.
 */
@SpringBootTest(classes = SimpleKanbanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskCardTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TaskCardTypeRepository taskCardTypeRepository;

    @Autowired
    private TaskCardTypeMapper taskCardTypeMapper;

    @Autowired
    private TaskCardTypeService taskCardTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskCardTypeMockMvc;

    private TaskCardType taskCardType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCardType createEntity(EntityManager em) {
        TaskCardType taskCardType = new TaskCardType()
            .name(DEFAULT_NAME);
        return taskCardType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCardType createUpdatedEntity(EntityManager em) {
        TaskCardType taskCardType = new TaskCardType()
            .name(UPDATED_NAME);
        return taskCardType;
    }

    @BeforeEach
    public void initTest() {
        taskCardType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskCardType() throws Exception {
        int databaseSizeBeforeCreate = taskCardTypeRepository.findAll().size();
        // Create the TaskCardType
        TaskCardTypeDTO taskCardTypeDTO = taskCardTypeMapper.toDto(taskCardType);
        restTaskCardTypeMockMvc.perform(post("/api/task-card-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskCardType in the database
        List<TaskCardType> taskCardTypeList = taskCardTypeRepository.findAll();
        assertThat(taskCardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TaskCardType testTaskCardType = taskCardTypeList.get(taskCardTypeList.size() - 1);
        assertThat(testTaskCardType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTaskCardTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskCardTypeRepository.findAll().size();

        // Create the TaskCardType with an existing ID
        taskCardType.setId(1L);
        TaskCardTypeDTO taskCardTypeDTO = taskCardTypeMapper.toDto(taskCardType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskCardTypeMockMvc.perform(post("/api/task-card-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCardType in the database
        List<TaskCardType> taskCardTypeList = taskCardTypeRepository.findAll();
        assertThat(taskCardTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCardTypeRepository.findAll().size();
        // set the field null
        taskCardType.setName(null);

        // Create the TaskCardType, which fails.
        TaskCardTypeDTO taskCardTypeDTO = taskCardTypeMapper.toDto(taskCardType);


        restTaskCardTypeMockMvc.perform(post("/api/task-card-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TaskCardType> taskCardTypeList = taskCardTypeRepository.findAll();
        assertThat(taskCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskCardTypes() throws Exception {
        // Initialize the database
        taskCardTypeRepository.saveAndFlush(taskCardType);

        // Get all the taskCardTypeList
        restTaskCardTypeMockMvc.perform(get("/api/task-card-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTaskCardType() throws Exception {
        // Initialize the database
        taskCardTypeRepository.saveAndFlush(taskCardType);

        // Get the taskCardType
        restTaskCardTypeMockMvc.perform(get("/api/task-card-types/{id}", taskCardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskCardType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTaskCardType() throws Exception {
        // Get the taskCardType
        restTaskCardTypeMockMvc.perform(get("/api/task-card-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskCardType() throws Exception {
        // Initialize the database
        taskCardTypeRepository.saveAndFlush(taskCardType);

        int databaseSizeBeforeUpdate = taskCardTypeRepository.findAll().size();

        // Update the taskCardType
        TaskCardType updatedTaskCardType = taskCardTypeRepository.findById(taskCardType.getId()).get();
        // Disconnect from session so that the updates on updatedTaskCardType are not directly saved in db
        em.detach(updatedTaskCardType);
        updatedTaskCardType
            .name(UPDATED_NAME);
        TaskCardTypeDTO taskCardTypeDTO = taskCardTypeMapper.toDto(updatedTaskCardType);

        restTaskCardTypeMockMvc.perform(put("/api/task-card-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TaskCardType in the database
        List<TaskCardType> taskCardTypeList = taskCardTypeRepository.findAll();
        assertThat(taskCardTypeList).hasSize(databaseSizeBeforeUpdate);
        TaskCardType testTaskCardType = taskCardTypeList.get(taskCardTypeList.size() - 1);
        assertThat(testTaskCardType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskCardType() throws Exception {
        int databaseSizeBeforeUpdate = taskCardTypeRepository.findAll().size();

        // Create the TaskCardType
        TaskCardTypeDTO taskCardTypeDTO = taskCardTypeMapper.toDto(taskCardType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskCardTypeMockMvc.perform(put("/api/task-card-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCardType in the database
        List<TaskCardType> taskCardTypeList = taskCardTypeRepository.findAll();
        assertThat(taskCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskCardType() throws Exception {
        // Initialize the database
        taskCardTypeRepository.saveAndFlush(taskCardType);

        int databaseSizeBeforeDelete = taskCardTypeRepository.findAll().size();

        // Delete the taskCardType
        restTaskCardTypeMockMvc.perform(delete("/api/task-card-types/{id}", taskCardType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskCardType> taskCardTypeList = taskCardTypeRepository.findAll();
        assertThat(taskCardTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
