package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class KanbanTableDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KanbanTableDTO.class);
        KanbanTableDTO kanbanTableDTO1 = new KanbanTableDTO();
        kanbanTableDTO1.setId(1L);
        KanbanTableDTO kanbanTableDTO2 = new KanbanTableDTO();
        assertThat(kanbanTableDTO1).isNotEqualTo(kanbanTableDTO2);
        kanbanTableDTO2.setId(kanbanTableDTO1.getId());
        assertThat(kanbanTableDTO1).isEqualTo(kanbanTableDTO2);
        kanbanTableDTO2.setId(2L);
        assertThat(kanbanTableDTO1).isNotEqualTo(kanbanTableDTO2);
        kanbanTableDTO1.setId(null);
        assertThat(kanbanTableDTO1).isNotEqualTo(kanbanTableDTO2);
    }
}
