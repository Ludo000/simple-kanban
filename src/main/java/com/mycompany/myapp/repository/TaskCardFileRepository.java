package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TaskCardFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskCardFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskCardFileRepository extends JpaRepository<TaskCardFile, Long> {
}
