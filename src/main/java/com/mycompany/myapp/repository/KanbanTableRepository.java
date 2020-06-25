package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.KanbanTable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the KanbanTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KanbanTableRepository extends JpaRepository<KanbanTable, Long> {

    @Query("select kanbanTable from KanbanTable kanbanTable where kanbanTable.user.login = ?#{principal.username}")
    List<KanbanTable> findByUserIsCurrentUser();
}
