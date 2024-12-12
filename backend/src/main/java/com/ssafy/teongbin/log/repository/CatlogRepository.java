package com.ssafy.teongbin.log.repository;

import com.ssafy.teongbin.log.entity.Catlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatlogRepository extends JpaRepository<Catlog, Long> {
}
