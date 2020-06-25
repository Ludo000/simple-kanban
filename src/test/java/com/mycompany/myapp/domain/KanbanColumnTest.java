package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class KanbanColumnTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KanbanColumn.class);
        KanbanColumn kanbanColumn1 = new KanbanColumn();
        kanbanColumn1.setId(1L);
        KanbanColumn kanbanColumn2 = new KanbanColumn();
        kanbanColumn2.setId(kanbanColumn1.getId());
        assertThat(kanbanColumn1).isEqualTo(kanbanColumn2);
        kanbanColumn2.setId(2L);
        assertThat(kanbanColumn1).isNotEqualTo(kanbanColumn2);
        kanbanColumn1.setId(null);
        assertThat(kanbanColumn1).isNotEqualTo(kanbanColumn2);
    }
}
