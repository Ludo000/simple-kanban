package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardImageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCardImage.class);
        TaskCardImage taskCardImage1 = new TaskCardImage();
        taskCardImage1.setId(1L);
        TaskCardImage taskCardImage2 = new TaskCardImage();
        taskCardImage2.setId(taskCardImage1.getId());
        assertThat(taskCardImage1).isEqualTo(taskCardImage2);
        taskCardImage2.setId(2L);
        assertThat(taskCardImage1).isNotEqualTo(taskCardImage2);
        taskCardImage1.setId(null);
        assertThat(taskCardImage1).isNotEqualTo(taskCardImage2);
    }
}
