package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCardDTO.class);
        TaskCardDTO taskCardDTO1 = new TaskCardDTO();
        taskCardDTO1.setId(1L);
        TaskCardDTO taskCardDTO2 = new TaskCardDTO();
        assertThat(taskCardDTO1).isNotEqualTo(taskCardDTO2);
        taskCardDTO2.setId(taskCardDTO1.getId());
        assertThat(taskCardDTO1).isEqualTo(taskCardDTO2);
        taskCardDTO2.setId(2L);
        assertThat(taskCardDTO1).isNotEqualTo(taskCardDTO2);
        taskCardDTO1.setId(null);
        assertThat(taskCardDTO1).isNotEqualTo(taskCardDTO2);
    }
}
