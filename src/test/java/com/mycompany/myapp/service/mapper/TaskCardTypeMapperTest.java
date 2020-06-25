package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskCardTypeMapperTest {

    private TaskCardTypeMapper taskCardTypeMapper;

    @BeforeEach
    public void setUp() {
        taskCardTypeMapper = new TaskCardTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskCardTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskCardTypeMapper.fromId(null)).isNull();
    }
}
