package com.ssafy.teongbin.shortcut.repository;

import com.ssafy.teongbin.shortcut.entity.Shortcut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortcutRepository extends JpaRepository<Shortcut, Long> {
    public List<Shortcut> findByUserId(Long UserId);
}
