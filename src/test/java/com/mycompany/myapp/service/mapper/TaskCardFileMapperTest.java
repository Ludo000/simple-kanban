package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskCardFileMapperTest {

    private TaskCardFileMapper taskCardFileMapper;

    @BeforeEach
    public void setUp() {
        taskCardFileMapper = new TaskCardFileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskCardFileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskCardFileMapper.fromId(null)).isNull();
    }
}
