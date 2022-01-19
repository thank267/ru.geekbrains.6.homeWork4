package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.dto.CategoryDto;
import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public Category dtoToEntity(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getTitle());
    }

    public CategoryDto entityToDto(Category category) {
        return new CategoryDto(category.getId(), category.getTitle());
    }
}
