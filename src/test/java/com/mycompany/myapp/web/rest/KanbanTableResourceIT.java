package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimpleKanbanApp;
import com.mycompany.myapp.domain.KanbanTable;
import com.mycompany.myapp.domain.KanbanColumn;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.KanbanTableRepository;
import com.mycompany.myapp.service.KanbanTableService;
import com.mycompany.myapp.service.dto.KanbanTableDTO;
import com.mycompany.myapp.service.mapper.KanbanTableMapper;
import com.mycompany.myapp.service.dto.KanbanTableCriteria;
import com.mycompany.myapp.service.KanbanTableQueryService;

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
    private KanbanTableQueryService kanbanTableQueryService;

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
    public void getKanbanTablesByIdFiltering() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        Long id = kanbanTable.getId();

        defaultKanbanTableShouldBeFound("id.equals=" + id);
        defaultKanbanTableShouldNotBeFound("id.notEquals=" + id);

        defaultKanbanTableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKanbanTableShouldNotBeFound("id.greaterThan=" + id);

        defaultKanbanTableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKanbanTableShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllKanbanTablesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where name equals to DEFAULT_NAME
        defaultKanbanTableShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the kanbanTableList where name equals to UPDATED_NAME
        defaultKanbanTableShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where name not equals to DEFAULT_NAME
        defaultKanbanTableShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the kanbanTableList where name not equals to UPDATED_NAME
        defaultKanbanTableShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where name in DEFAULT_NAME or UPDATED_NAME
        defaultKanbanTableShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the kanbanTableList where name equals to UPDATED_NAME
        defaultKanbanTableShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where name is not null
        defaultKanbanTableShouldBeFound("name.specified=true");

        // Get all the kanbanTableList where name is null
        defaultKanbanTableShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllKanbanTablesByNameContainsSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where name contains DEFAULT_NAME
        defaultKanbanTableShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the kanbanTableList where name contains UPDATED_NAME
        defaultKanbanTableShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where name does not contain DEFAULT_NAME
        defaultKanbanTableShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the kanbanTableList where name does not contain UPDATED_NAME
        defaultKanbanTableShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllKanbanTablesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where description equals to DEFAULT_DESCRIPTION
        defaultKanbanTableShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the kanbanTableList where description equals to UPDATED_DESCRIPTION
        defaultKanbanTableShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where description not equals to DEFAULT_DESCRIPTION
        defaultKanbanTableShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the kanbanTableList where description not equals to UPDATED_DESCRIPTION
        defaultKanbanTableShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultKanbanTableShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the kanbanTableList where description equals to UPDATED_DESCRIPTION
        defaultKanbanTableShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where description is not null
        defaultKanbanTableShouldBeFound("description.specified=true");

        // Get all the kanbanTableList where description is null
        defaultKanbanTableShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllKanbanTablesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where description contains DEFAULT_DESCRIPTION
        defaultKanbanTableShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the kanbanTableList where description contains UPDATED_DESCRIPTION
        defaultKanbanTableShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where description does not contain DEFAULT_DESCRIPTION
        defaultKanbanTableShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the kanbanTableList where description does not contain UPDATED_DESCRIPTION
        defaultKanbanTableShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllKanbanTablesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where creationDate equals to DEFAULT_CREATION_DATE
        defaultKanbanTableShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the kanbanTableList where creationDate equals to UPDATED_CREATION_DATE
        defaultKanbanTableShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultKanbanTableShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the kanbanTableList where creationDate not equals to UPDATED_CREATION_DATE
        defaultKanbanTableShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultKanbanTableShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the kanbanTableList where creationDate equals to UPDATED_CREATION_DATE
        defaultKanbanTableShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where creationDate is not null
        defaultKanbanTableShouldBeFound("creationDate.specified=true");

        // Get all the kanbanTableList where creationDate is null
        defaultKanbanTableShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByModificationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where modificationDate equals to DEFAULT_MODIFICATION_DATE
        defaultKanbanTableShouldBeFound("modificationDate.equals=" + DEFAULT_MODIFICATION_DATE);

        // Get all the kanbanTableList where modificationDate equals to UPDATED_MODIFICATION_DATE
        defaultKanbanTableShouldNotBeFound("modificationDate.equals=" + UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByModificationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where modificationDate not equals to DEFAULT_MODIFICATION_DATE
        defaultKanbanTableShouldNotBeFound("modificationDate.notEquals=" + DEFAULT_MODIFICATION_DATE);

        // Get all the kanbanTableList where modificationDate not equals to UPDATED_MODIFICATION_DATE
        defaultKanbanTableShouldBeFound("modificationDate.notEquals=" + UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByModificationDateIsInShouldWork() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where modificationDate in DEFAULT_MODIFICATION_DATE or UPDATED_MODIFICATION_DATE
        defaultKanbanTableShouldBeFound("modificationDate.in=" + DEFAULT_MODIFICATION_DATE + "," + UPDATED_MODIFICATION_DATE);

        // Get all the kanbanTableList where modificationDate equals to UPDATED_MODIFICATION_DATE
        defaultKanbanTableShouldNotBeFound("modificationDate.in=" + UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByModificationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);

        // Get all the kanbanTableList where modificationDate is not null
        defaultKanbanTableShouldBeFound("modificationDate.specified=true");

        // Get all the kanbanTableList where modificationDate is null
        defaultKanbanTableShouldNotBeFound("modificationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllKanbanTablesByTablesIsEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);
        KanbanColumn tables = KanbanColumnResourceIT.createEntity(em);
        em.persist(tables);
        em.flush();
        kanbanTable.addTables(tables);
        kanbanTableRepository.saveAndFlush(kanbanTable);
        Long tablesId = tables.getId();

        // Get all the kanbanTableList where tables equals to tablesId
        defaultKanbanTableShouldBeFound("tablesId.equals=" + tablesId);

        // Get all the kanbanTableList where tables equals to tablesId + 1
        defaultKanbanTableShouldNotBeFound("tablesId.equals=" + (tablesId + 1));
    }


    @Test
    @Transactional
    public void getAllKanbanTablesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        kanbanTableRepository.saveAndFlush(kanbanTable);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        kanbanTable.setUser(user);
        kanbanTableRepository.saveAndFlush(kanbanTable);
        Long userId = user.getId();

        // Get all the kanbanTableList where user equals to userId
        defaultKanbanTableShouldBeFound("userId.equals=" + userId);

        // Get all the kanbanTableList where user equals to userId + 1
        defaultKanbanTableShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKanbanTableShouldBeFound(String filter) throws Exception {
        restKanbanTableMockMvc.perform(get("/api/kanban-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kanbanTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));

        // Check, that the count call also returns 1
        restKanbanTableMockMvc.perform(get("/api/kanban-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKanbanTableShouldNotBeFound(String filter) throws Exception {
        restKanbanTableMockMvc.perform(get("/api/kanban-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKanbanTableMockMvc.perform(get("/api/kanban-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
