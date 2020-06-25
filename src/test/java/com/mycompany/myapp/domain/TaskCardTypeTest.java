package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCardType.class);
        TaskCardType taskCardType1 = new TaskCardType();
        taskCardType1.setId(1L);
        TaskCardType taskCardType2 = new TaskCardType();
        taskCardType2.setId(taskCardType1.getId());
        assertThat(taskCardType1).isEqualTo(taskCardType2);
        taskCardType2.setId(2L);
        assertThat(taskCardType1).isNotEqualTo(taskCardType2);
        taskCardType1.setId(null);
        assertThat(taskCardType1).isNotEqualTo(taskCardType2);
    }
}
