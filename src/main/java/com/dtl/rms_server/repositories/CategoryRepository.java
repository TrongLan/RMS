package com.dtl.rms_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtl.rms_server.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
