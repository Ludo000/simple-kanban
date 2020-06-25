package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class KanbanColumnDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KanbanColumnDTO.class);
        KanbanColumnDTO kanbanColumnDTO1 = new KanbanColumnDTO();
        kanbanColumnDTO1.setId(1L);
        KanbanColumnDTO kanbanColumnDTO2 = new KanbanColumnDTO();
        assertThat(kanbanColumnDTO1).isNotEqualTo(kanbanColumnDTO2);
        kanbanColumnDTO2.setId(kanbanColumnDTO1.getId());
        assertThat(kanbanColumnDTO1).isEqualTo(kanbanColumnDTO2);
        kanbanColumnDTO2.setId(2L);
        assertThat(kanbanColumnDTO1).isNotEqualTo(kanbanColumnDTO2);
        kanbanColumnDTO1.setId(null);
        assertThat(kanbanColumnDTO1).isNotEqualTo(kanbanColumnDTO2);
    }
}
