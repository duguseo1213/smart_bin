package com.ssafy.teongbin.log.initialdata;

import com.ssafy.teongbin.log.entity.Category;
import com.ssafy.teongbin.log.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void loadInitialData() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("캔");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("플라스틱");

        Category category3 = new Category();
        category3.setId(3L);
        category3.setName("일반쓰레기");

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
    }
}
