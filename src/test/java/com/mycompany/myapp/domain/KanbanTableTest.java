package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class KanbanTableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KanbanTable.class);
        KanbanTable kanbanTable1 = new KanbanTable();
        kanbanTable1.setId(1L);
        KanbanTable kanbanTable2 = new KanbanTable();
        kanbanTable2.setId(kanbanTable1.getId());
        assertThat(kanbanTable1).isEqualTo(kanbanTable2);
        kanbanTable2.setId(2L);
        assertThat(kanbanTable1).isNotEqualTo(kanbanTable2);
        kanbanTable1.setId(null);
        assertThat(kanbanTable1).isNotEqualTo(kanbanTable2);
    }
}
