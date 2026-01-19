package com.example.demo.Repositories;

import com.example.demo.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsByName(String name);

}
