package com.JonathanJ00.task_list_backend.repository;

import com.JonathanJ00.task_list_backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
