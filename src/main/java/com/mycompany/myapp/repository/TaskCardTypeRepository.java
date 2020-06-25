package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TaskCardType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskCardType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskCardTypeRepository extends JpaRepository<TaskCardType, Long> {
}
