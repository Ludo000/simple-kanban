package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TaskCardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCard.class);
        TaskCard taskCard1 = new TaskCard();
        taskCard1.setId(1L);
        TaskCard taskCard2 = new TaskCard();
        taskCard2.setId(taskCard1.getId());
        assertThat(taskCard1).isEqualTo(taskCard2);
        taskCard2.setId(2L);
        assertThat(taskCard1).isNotEqualTo(taskCard2);
        taskCard1.setId(null);
        assertThat(taskCard1).isNotEqualTo(taskCard2);
    }
}
