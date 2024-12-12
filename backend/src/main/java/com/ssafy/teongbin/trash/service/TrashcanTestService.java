package com.ssafy.teongbin.trash.service;

import com.ssafy.teongbin.trash.entity.Trashcan;
import com.ssafy.teongbin.trash.repository.TrashcanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrashcanTestService {
    private final TrashcanRepository trashcanRepository;

    public List<Trashcan> findTrashcans() {
        return trashcanRepository.findAll();
    }
}
