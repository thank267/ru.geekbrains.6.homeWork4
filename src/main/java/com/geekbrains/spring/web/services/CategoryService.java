package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.CategoriesRepository;
import com.geekbrains.spring.web.repositories.ProductsRepository;
import com.geekbrains.spring.web.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

        public List<Category> findAll() {

        return categoriesRepository.findAll();
    }

    public Category findById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена в базе: id: " + id));
    }


}
