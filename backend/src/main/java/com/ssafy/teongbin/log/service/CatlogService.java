package com.ssafy.teongbin.log.service;

import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.log.dto.request.CatlogRequest;
import com.ssafy.teongbin.log.entity.Category;
import com.ssafy.teongbin.log.entity.Catlog;
import com.ssafy.teongbin.log.repository.CategoryRepository;
import com.ssafy.teongbin.log.repository.CatlogRepository;
import com.ssafy.teongbin.trash.entity.Trashcan;
import com.ssafy.teongbin.trash.repository.TrashcanRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CatlogService {
    private final CatlogRepository catlogRepository;
    private final TrashcanRepository trashcanRepository;
    private final CategoryRepository categoryRepository;
    private final EntityManager em;

    public Long join(CatlogRequest request) {
        Long categoryId = request.getCategoryId();
        if (categoryId==null || categoryId<=0) {
            throw new CustomException(ErrorType.FAILED_TO_CATEGORYID);
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CustomException(ErrorType.NOT_FOUND_CATEGORYID));
        String categoryname = category.getName();
        if (categoryname==null) {
            throw new CustomException(ErrorType.NOT_FOUND_CATEGORYNAME);
        }
        String sn = request.getSerialNumber();
        if (sn==null || sn.trim().isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_SERIAL);
        }
        Trashcan trashcan = trashcanRepository.findBySerialNumber(sn);
        if (trashcan==null) {
            throw new CustomException(ErrorType.NOT_FOUND_TRASHCAN);
        }
        Catlog catlog = new Catlog();
        catlog.setCategory(category);
        catlog.setTrashcan(trashcan);
        catlogRepository.save(catlog);
        Long catlogId = catlog.getId();
        if (catlogId==null) {
            throw new CustomException(ErrorType.FAILED_TO_CATLOG);
        }
        return catlogId;
    }
}
