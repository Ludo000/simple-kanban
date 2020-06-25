package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.KanbanColumn;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KanbanColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KanbanColumnRepository extends JpaRepository<KanbanColumn, Long> {
}
