package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCardFile.class);
        TaskCardFile taskCardFile1 = new TaskCardFile();
        taskCardFile1.setId(1L);
        TaskCardFile taskCardFile2 = new TaskCardFile();
        taskCardFile2.setId(taskCardFile1.getId());
        assertThat(taskCardFile1).isEqualTo(taskCardFile2);
        taskCardFile2.setId(2L);
        assertThat(taskCardFile1).isNotEqualTo(taskCardFile2);
        taskCardFile1.setId(null);
        assertThat(taskCardFile1).isNotEqualTo(taskCardFile2);
    }
}
