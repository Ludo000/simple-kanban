package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardFileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCardFileDTO.class);
        TaskCardFileDTO taskCardFileDTO1 = new TaskCardFileDTO();
        taskCardFileDTO1.setId(1L);
        TaskCardFileDTO taskCardFileDTO2 = new TaskCardFileDTO();
        assertThat(taskCardFileDTO1).isNotEqualTo(taskCardFileDTO2);
        taskCardFileDTO2.setId(taskCardFileDTO1.getId());
        assertThat(taskCardFileDTO1).isEqualTo(taskCardFileDTO2);
        taskCardFileDTO2.setId(2L);
        assertThat(taskCardFileDTO1).isNotEqualTo(taskCardFileDTO2);
        taskCardFileDTO1.setId(null);
        assertThat(taskCardFileDTO1).isNotEqualTo(taskCardFileDTO2);
    }
}
