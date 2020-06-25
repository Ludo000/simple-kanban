package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TaskCardImage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskCardImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskCardImageRepository extends JpaRepository<TaskCardImage, Long> {
}
