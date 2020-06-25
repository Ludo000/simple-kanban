package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimpleKanbanApp;
import com.mycompany.myapp.domain.KanbanTable;
import com.mycompany.myapp.repository.KanbanTableRepository;
import com.mycompany.myapp.service.KanbanTableService;
import com.mycompany.myapp.service.dto.KanbanTableDTO;
import com.mycompany.myapp.service.mapper.KanbanTableMapper;

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
 * Integration tests for the {@link KanbanTableResource} REST controller.
 */
@SpringBootTest(classes = SimpleKanbanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KanbanTableResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private KanbanTableRepository kanbanTableRepository;

    @Autowired
    private KanbanTableMapper kanbanTableMapper;

    @Autowired
    private KanbanTableService kanbanTableService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKanbanTableMockMvc;

    private KanbanTable kanbanTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanbanTable createEntity(EntityManager em) {
        KanbanTable kanbanTable = new KanbanTable()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .creationDate(DEFAULT_CREATION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE);
        return kanbanTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanbanTable createUpdatedEntity(EntityManager em) {
        KanbanTable kanbanTable = new KanbanTable()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        return kanbanTable;
    }

    @BeforeEach
    public void initTest() {
        kanbanTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createKanbanTable() throws Exception {
        int databaseSizeBeforeCreate = kanbanTableRepository.findAll().size();
        // Create the KanbanTable
        KanbanTableDTO kanbanTableDTO = kanbanTableMapper.toDto(kanbanTable);
        restKanbanTableMockMvc.perform(post("/api/kanban-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanTableDTO)))
            .andExpect(status().isCreated());

        // Validate the KanbanTable in the database
        List<KanbanTable> kanbanTableList = kanbanTableRepository.findAll();
        assertThat(kanbanTableList).hasSize(databaseSizeBeforeCreate + 1);
        KanbanTable testKanbanTable = kanbanTableList.get(kanbanTableList.size() - 1);
        assertThat(testKanbanTable.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKanbanTable.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testKanbanTable.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testKanbanTable.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void createKanbanTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kanbanTableRepository.findAll().size();

        // Create the KanbanTable with an existing ID
        kanbanTable.setId(1L);
        KanbanTableDTO kanbanTableDTO = kanbanTableMapper.toDto(kanbanTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKanbanTableMockMvc.perform(post("/api/kanban-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KanbanTable in the database
        List<KanbanTable> kanbanTableList = kanbanTableRepository.findAll();
        assertThat(kanbanTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = kanbanTableRepository.findAll().size();
        // set the field null
        kanbanTable.setCreationDate(null);

        // Create the KanbanTable, which fails.
        KanbanTableDTO kanbanTableDTO = kanbanTableMapper.toDto(kanbanTable);


        restKanbanTableMockMvc.perform(post("/api/kanban-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanTableDTO)))
            .andExpect(status().isBadRequest());

        List<KanbanTable> kanbanTableList = kanbanTableRepository.findAll();
        assertThat(kanbanTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = kanbanTableRepository.findAll().size();
        // set the field null
        kanbanTable.setModificationDate(null);

        // Create the KanbanTable, which fails.
        KanbanTableDTO kanbanTableDTO = kanbanTableMapper.toDto(kanbanTable);


        restKanbanTableMockMvc.perform(post("/api/kanban-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanTableDTO)))
            .andExpect(status().isBadRequest());

        List<KanbanTable> kanbanTableList = kanbanTableRepository.findAll();
        assertThat(kanbanTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKanbanTables() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList
        restKanbanTableMockMvc.perform(get("/api/kanban-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kanbanTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getKanbanTable() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get the kanbanTable
        restKanbanTableMockMvc.perform(get("/api/kanban-tables/{id}", kanbanTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kanbanTable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingKanbanTable() throws Exception {
        // Get the kanbanTable
        restKanbanTableMockMvc.perform(get("/api/kanban-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanbanTable() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        int databaseSizeBeforeUpdate = kanbanTableRepository.findAll().size();

        // Update the kanbanTable
        KanbanTable updatedKanbanTable = kanbanTableRepository.findById(kanbanTable.getId()).get();
        // Disconnect from session so that the updates on updatedKanbanTable are not directly saved in db
        em.detach(updatedKanbanTable);
        updatedKanbanTable
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        KanbanTableDTO kanbanTableDTO = kanbanTableMapper.toDto(updatedKanbanTable);

        restKanbanTableMockMvc.perform(put("/api/kanban-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanTableDTO)))
            .andExpect(status().isOk());

        // Validate the KanbanTable in the database
        List<KanbanTable> kanbanTableList = kanbanTableRepository.findAll();
        assertThat(kanbanTableList).hasSize(databaseSizeBeforeUpdate);
        KanbanTable testKanbanTable = kanbanTableList.get(kanbanTableList.size() - 1);
        assertThat(testKanbanTable.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKanbanTable.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testKanbanTable.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testKanbanTable.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingKanbanTable() throws Exception {
        int databaseSizeBeforeUpdate = kanbanTableRepository.findAll().size();

        // Create the KanbanTable
        KanbanTableDTO kanbanTableDTO = kanbanTableMapper.toDto(kanbanTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKanbanTableMockMvc.perform(put("/api/kanban-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KanbanTable in the database
        List<KanbanTable> kanbanTableList = kanbanTableRepository.findAll();
        assertThat(kanbanTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKanbanTable() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        int databaseSizeBeforeDelete = kanbanTableRepository.findAll().size();

        // Delete the kanbanTable
        restKanbanTableMockMvc.perform(delete("/api/kanban-tables/{id}", kanbanTable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KanbanTable> kanbanTableList = kanbanTableRepository.findAll();
        assertThat(kanbanTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
