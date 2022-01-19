package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.controllers.CategoriesController;
import com.geekbrains.spring.web.converters.CategoryConverter;
import com.geekbrains.spring.web.dto.CategoryDto;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;

    public static final Function<Product, com.geekbrains.spring.web.soap.products.Product> functionEntityToSoap = p -> {
        com.geekbrains.spring.web.soap.products.Product ps = new com.geekbrains.spring.web.soap.products.Product();
        ps.setId(p.getId());
        ps.setTitle(p.getTitle());
        ps.setPrice(p.getPrice());
        return ps;
    };


    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partTitle, Long categoryId, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }

        if (categoryId != null) {
            spec = spec.and(ProductsSpecifications.belongsToCategory(Arrays.asList(categoriesRepository.getById(categoryId))));
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    public List<com.geekbrains.spring.web.soap.products.Product> findAll() {
        return productsRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public Optional<com.geekbrains.spring.web.soap.products.Product> findByIdSoap(Long id) {
        return productsRepository.findById(id).stream().map(functionEntityToSoap).findFirst();
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }
}
