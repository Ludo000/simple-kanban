package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KanbanColumnMapperTest {

    private KanbanColumnMapper kanbanColumnMapper;

    @BeforeEach
    public void setUp() {
        kanbanColumnMapper = new KanbanColumnMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(kanbanColumnMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(kanbanColumnMapper.fromId(null)).isNull();
    }
}
