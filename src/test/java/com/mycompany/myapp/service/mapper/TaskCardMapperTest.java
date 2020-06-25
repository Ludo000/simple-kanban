package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskCardMapperTest {

    private TaskCardMapper taskCardMapper;

    @BeforeEach
    public void setUp() {
        taskCardMapper = new TaskCardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskCardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskCardMapper.fromId(null)).isNull();
    }
}
