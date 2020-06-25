package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KanbanTableMapperTest {

    private KanbanTableMapper kanbanTableMapper;

    @BeforeEach
    public void setUp() {
        kanbanTableMapper = new KanbanTableMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(kanbanTableMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(kanbanTableMapper.fromId(null)).isNull();
    }
}
