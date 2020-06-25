package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimpleKanbanApp;
import com.mycompany.myapp.domain.KanbanColumn;
import com.mycompany.myapp.repository.KanbanColumnRepository;
import com.mycompany.myapp.service.KanbanColumnService;
import com.mycompany.myapp.service.dto.KanbanColumnDTO;
import com.mycompany.myapp.service.mapper.KanbanColumnMapper;

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
 * Integration tests for the {@link KanbanColumnResource} REST controller.
 */
@SpringBootTest(classes = SimpleKanbanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KanbanColumnResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private KanbanColumnRepository kanbanColumnRepository;

    @Autowired
    private KanbanColumnMapper kanbanColumnMapper;

    @Autowired
    private KanbanColumnService kanbanColumnService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKanbanColumnMockMvc;

    private KanbanColumn kanbanColumn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanbanColumn createEntity(EntityManager em) {
        KanbanColumn kanbanColumn = new KanbanColumn()
            .name(DEFAULT_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE);
        return kanbanColumn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanbanColumn createUpdatedEntity(EntityManager em) {
        KanbanColumn kanbanColumn = new KanbanColumn()
            .name(UPDATED_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        return kanbanColumn;
    }

    @BeforeEach
    public void initTest() {
        kanbanColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createKanbanColumn() throws Exception {
        int databaseSizeBeforeCreate = kanbanColumnRepository.findAll().size();
        // Create the KanbanColumn
        KanbanColumnDTO kanbanColumnDTO = kanbanColumnMapper.toDto(kanbanColumn);
        restKanbanColumnMockMvc.perform(post("/api/kanban-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanColumnDTO)))
            .andExpect(status().isCreated());

        // Validate the KanbanColumn in the database
        List<KanbanColumn> kanbanColumnList = kanbanColumnRepository.findAll();
        assertThat(kanbanColumnList).hasSize(databaseSizeBeforeCreate + 1);
        KanbanColumn testKanbanColumn = kanbanColumnList.get(kanbanColumnList.size() - 1);
        assertThat(testKanbanColumn.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKanbanColumn.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testKanbanColumn.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void createKanbanColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kanbanColumnRepository.findAll().size();

        // Create the KanbanColumn with an existing ID
        kanbanColumn.setId(1L);
        KanbanColumnDTO kanbanColumnDTO = kanbanColumnMapper.toDto(kanbanColumn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKanbanColumnMockMvc.perform(post("/api/kanban-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KanbanColumn in the database
        List<KanbanColumn> kanbanColumnList = kanbanColumnRepository.findAll();
        assertThat(kanbanColumnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = kanbanColumnRepository.findAll().size();
        // set the field null
        kanbanColumn.setCreationDate(null);

        // Create the KanbanColumn, which fails.
        KanbanColumnDTO kanbanColumnDTO = kanbanColumnMapper.toDto(kanbanColumn);


        restKanbanColumnMockMvc.perform(post("/api/kanban-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanColumnDTO)))
            .andExpect(status().isBadRequest());

        List<KanbanColumn> kanbanColumnList = kanbanColumnRepository.findAll();
        assertThat(kanbanColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = kanbanColumnRepository.findAll().size();
        // set the field null
        kanbanColumn.setModificationDate(null);

        // Create the KanbanColumn, which fails.
        KanbanColumnDTO kanbanColumnDTO = kanbanColumnMapper.toDto(kanbanColumn);


        restKanbanColumnMockMvc.perform(post("/api/kanban-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanColumnDTO)))
            .andExpect(status().isBadRequest());

        List<KanbanColumn> kanbanColumnList = kanbanColumnRepository.findAll();
        assertThat(kanbanColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKanbanColumns() throws Exception {
        // Initialize the database
        kanbanColumnRepository.saveAndFlush(kanbanColumn);

        // Get all the kanbanColumnList
        restKanbanColumnMockMvc.perform(get("/api/kanban-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kanbanColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getKanbanColumn() throws Exception {
        // Initialize the database
        kanbanColumnRepository.saveAndFlush(kanbanColumn);

        // Get the kanbanColumn
        restKanbanColumnMockMvc.perform(get("/api/kanban-columns/{id}", kanbanColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kanbanColumn.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingKanbanColumn() throws Exception {
        // Get the kanbanColumn
        restKanbanColumnMockMvc.perform(get("/api/kanban-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanbanColumn() throws Exception {
        // Initialize the database
        kanbanColumnRepository.saveAndFlush(kanbanColumn);

        int databaseSizeBeforeUpdate = kanbanColumnRepository.findAll().size();

        // Update the kanbanColumn
        KanbanColumn updatedKanbanColumn = kanbanColumnRepository.findById(kanbanColumn.getId()).get();
        // Disconnect from session so that the updates on updatedKanbanColumn are not directly saved in db
        em.detach(updatedKanbanColumn);
        updatedKanbanColumn
            .name(UPDATED_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        KanbanColumnDTO kanbanColumnDTO = kanbanColumnMapper.toDto(updatedKanbanColumn);

        restKanbanColumnMockMvc.perform(put("/api/kanban-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanColumnDTO)))
            .andExpect(status().isOk());

        // Validate the KanbanColumn in the database
        List<KanbanColumn> kanbanColumnList = kanbanColumnRepository.findAll();
        assertThat(kanbanColumnList).hasSize(databaseSizeBeforeUpdate);
        KanbanColumn testKanbanColumn = kanbanColumnList.get(kanbanColumnList.size() - 1);
        assertThat(testKanbanColumn.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKanbanColumn.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testKanbanColumn.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingKanbanColumn() throws Exception {
        int databaseSizeBeforeUpdate = kanbanColumnRepository.findAll().size();

        // Create the KanbanColumn
        KanbanColumnDTO kanbanColumnDTO = kanbanColumnMapper.toDto(kanbanColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKanbanColumnMockMvc.perform(put("/api/kanban-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanbanColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KanbanColumn in the database
        List<KanbanColumn> kanbanColumnList = kanbanColumnRepository.findAll();
        assertThat(kanbanColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKanbanColumn() throws Exception {
        // Initialize the database
        kanbanColumnRepository.saveAndFlush(kanbanColumn);

        int databaseSizeBeforeDelete = kanbanColumnRepository.findAll().size();

        // Delete the kanbanColumn
        restKanbanColumnMockMvc.perform(delete("/api/kanban-columns/{id}", kanbanColumn.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KanbanColumn> kanbanColumnList = kanbanColumnRepository.findAll();
        assertThat(kanbanColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
