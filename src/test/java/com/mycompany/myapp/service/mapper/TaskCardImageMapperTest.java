package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskCardImageMapperTest {

    private TaskCardImageMapper taskCardImageMapper;

    @BeforeEach
    public void setUp() {
        taskCardImageMapper = new TaskCardImageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskCardImageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskCardImageMapper.fromId(null)).isNull();
    }
}
