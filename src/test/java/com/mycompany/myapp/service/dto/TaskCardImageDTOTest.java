package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardImageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCardImageDTO.class);
        TaskCardImageDTO taskCardImageDTO1 = new TaskCardImageDTO();
        taskCardImageDTO1.setId(1L);
        TaskCardImageDTO taskCardImageDTO2 = new TaskCardImageDTO();
        assertThat(taskCardImageDTO1).isNotEqualTo(taskCardImageDTO2);
        taskCardImageDTO2.setId(taskCardImageDTO1.getId());
        assertThat(taskCardImageDTO1).isEqualTo(taskCardImageDTO2);
        taskCardImageDTO2.setId(2L);
        assertThat(taskCardImageDTO1).isNotEqualTo(taskCardImageDTO2);
        taskCardImageDTO1.setId(null);
        assertThat(taskCardImageDTO1).isNotEqualTo(taskCardImageDTO2);
    }
}
