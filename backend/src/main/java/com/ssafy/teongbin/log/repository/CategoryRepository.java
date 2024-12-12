package com.ssafy.teongbin.log.repository;

import com.ssafy.teongbin.log.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
