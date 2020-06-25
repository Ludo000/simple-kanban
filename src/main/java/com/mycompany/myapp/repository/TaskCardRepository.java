package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TaskCard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {
}
