package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimpleKanbanApp;
import com.mycompany.myapp.domain.TaskCard;
import com.mycompany.myapp.repository.TaskCardRepository;
import com.mycompany.myapp.service.TaskCardService;
import com.mycompany.myapp.service.dto.TaskCardDTO;
import com.mycompany.myapp.service.mapper.TaskCardMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaskCardResource} REST controller.
 */
@SpringBootTest(classes = SimpleKanbanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskCardResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_HEX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_HEX_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LIMIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LIMIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskCardRepository taskCardRepository;

    @Autowired
    private TaskCardMapper taskCardMapper;

    @Autowired
    private TaskCardService taskCardService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskCardMockMvc;

    private TaskCard taskCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCard createEntity(EntityManager em) {
        TaskCard taskCard = new TaskCard()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .colorHexCode(DEFAULT_COLOR_HEX_CODE)
            .creationDate(DEFAULT_CREATION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE)
            .limitDate(DEFAULT_LIMIT_DATE);
        return taskCard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCard createUpdatedEntity(EntityManager em) {
        TaskCard taskCard = new TaskCard()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .colorHexCode(UPDATED_COLOR_HEX_CODE)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .limitDate(UPDATED_LIMIT_DATE);
        return taskCard;
    }

    @BeforeEach
    public void initTest() {
        taskCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskCard() throws Exception {
        int databaseSizeBeforeCreate = taskCardRepository.findAll().size();
        // Create the TaskCard
        TaskCardDTO taskCardDTO = taskCardMapper.toDto(taskCard);
        restTaskCardMockMvc.perform(post("/api/task-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskCard in the database
        List<TaskCard> taskCardList = taskCardRepository.findAll();
        assertThat(taskCardList).hasSize(databaseSizeBeforeCreate + 1);
        TaskCard testTaskCard = taskCardList.get(taskCardList.size() - 1);
        assertThat(testTaskCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskCard.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaskCard.getColorHexCode()).isEqualTo(DEFAULT_COLOR_HEX_CODE);
        assertThat(testTaskCard.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTaskCard.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testTaskCard.getLimitDate()).isEqualTo(DEFAULT_LIMIT_DATE);
    }

    @Test
    @Transactional
    public void createTaskCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskCardRepository.findAll().size();

        // Create the TaskCard with an existing ID
        taskCard.setId(1L);
        TaskCardDTO taskCardDTO = taskCardMapper.toDto(taskCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskCardMockMvc.perform(post("/api/task-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCard in the database
        List<TaskCard> taskCardList = taskCardRepository.findAll();
        assertThat(taskCardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCardRepository.findAll().size();
        // set the field null
        taskCard.setCreationDate(null);

        // Create the TaskCard, which fails.
        TaskCardDTO taskCardDTO = taskCardMapper.toDto(taskCard);


        restTaskCardMockMvc.perform(post("/api/task-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardDTO)))
            .andExpect(status().isBadRequest());

        List<TaskCard> taskCardList = taskCardRepository.findAll();
        assertThat(taskCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCardRepository.findAll().size();
        // set the field null
        taskCard.setModificationDate(null);

        // Create the TaskCard, which fails.
        TaskCardDTO taskCardDTO = taskCardMapper.toDto(taskCard);


        restTaskCardMockMvc.perform(post("/api/task-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardDTO)))
            .andExpect(status().isBadRequest());

        List<TaskCard> taskCardList = taskCardRepository.findAll();
        assertThat(taskCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskCards() throws Exception {
        // Initialize the database
        taskCardRepository.saveAndFlush(taskCard);

        // Get all the taskCardList
        restTaskCardMockMvc.perform(get("/api/task-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].colorHexCode").value(hasItem(DEFAULT_COLOR_HEX_CODE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].limitDate").value(hasItem(DEFAULT_LIMIT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTaskCard() throws Exception {
        // Initialize the database
        taskCardRepository.saveAndFlush(taskCard);

        // Get the taskCard
        restTaskCardMockMvc.perform(get("/api/task-cards/{id}", taskCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskCard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.colorHexCode").value(DEFAULT_COLOR_HEX_CODE))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.limitDate").value(DEFAULT_LIMIT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaskCard() throws Exception {
        // Get the taskCard
        restTaskCardMockMvc.perform(get("/api/task-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskCard() throws Exception {
        // Initialize the database
        taskCardRepository.saveAndFlush(taskCard);

        int databaseSizeBeforeUpdate = taskCardRepository.findAll().size();

        // Update the taskCard
        TaskCard updatedTaskCard = taskCardRepository.findById(taskCard.getId()).get();
        // Disconnect from session so that the updates on updatedTaskCard are not directly saved in db
        em.detach(updatedTaskCard);
        updatedTaskCard
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .colorHexCode(UPDATED_COLOR_HEX_CODE)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .limitDate(UPDATED_LIMIT_DATE);
        TaskCardDTO taskCardDTO = taskCardMapper.toDto(updatedTaskCard);

        restTaskCardMockMvc.perform(put("/api/task-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardDTO)))
            .andExpect(status().isOk());

        // Validate the TaskCard in the database
        List<TaskCard> taskCardList = taskCardRepository.findAll();
        assertThat(taskCardList).hasSize(databaseSizeBeforeUpdate);
        TaskCard testTaskCard = taskCardList.get(taskCardList.size() - 1);
        assertThat(testTaskCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskCard.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaskCard.getColorHexCode()).isEqualTo(UPDATED_COLOR_HEX_CODE);
        assertThat(testTaskCard.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTaskCard.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testTaskCard.getLimitDate()).isEqualTo(UPDATED_LIMIT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskCard() throws Exception {
        int databaseSizeBeforeUpdate = taskCardRepository.findAll().size();

        // Create the TaskCard
        TaskCardDTO taskCardDTO = taskCardMapper.toDto(taskCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskCardMockMvc.perform(put("/api/task-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskCard in the database
        List<TaskCard> taskCardList = taskCardRepository.findAll();
        assertThat(taskCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskCard() throws Exception {
        // Initialize the database
        taskCardRepository.saveAndFlush(taskCard);

        int databaseSizeBeforeDelete = taskCardRepository.findAll().size();

        // Delete the taskCard
        restTaskCardMockMvc.perform(delete("/api/task-cards/{id}", taskCard.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskCard> taskCardList = taskCardRepository.findAll();
        assertThat(taskCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
