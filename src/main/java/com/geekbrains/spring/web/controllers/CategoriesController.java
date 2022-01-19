package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.CategoryConverter;
import com.geekbrains.spring.web.converters.ProductConverter;
import com.geekbrains.spring.web.dto.CategoryDto;
import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.services.CategoryService;
import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @GetMapping
    public List<CategoryDto> getAllCategories( ) {

        return categoryService
                .findAll()
                .stream()
                .map(
                    c -> categoryConverter.entityToDto(c))
                .collect(Collectors.toList());
    }
}
