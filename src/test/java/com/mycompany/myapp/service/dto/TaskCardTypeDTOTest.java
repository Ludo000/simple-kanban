package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCardTypeDTO.class);
        TaskCardTypeDTO taskCardTypeDTO1 = new TaskCardTypeDTO();
        taskCardTypeDTO1.setId(1L);
        TaskCardTypeDTO taskCardTypeDTO2 = new TaskCardTypeDTO();
        assertThat(taskCardTypeDTO1).isNotEqualTo(taskCardTypeDTO2);
        taskCardTypeDTO2.setId(taskCardTypeDTO1.getId());
        assertThat(taskCardTypeDTO1).isEqualTo(taskCardTypeDTO2);
        taskCardTypeDTO2.setId(2L);
        assertThat(taskCardTypeDTO1).isNotEqualTo(taskCardTypeDTO2);
        taskCardTypeDTO1.setId(null);
        assertThat(taskCardTypeDTO1).isNotEqualTo(taskCardTypeDTO2);
    }
}
