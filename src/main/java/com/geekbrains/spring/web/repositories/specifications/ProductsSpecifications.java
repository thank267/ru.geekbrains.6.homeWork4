package com.geekbrains.spring.web.repositories.specifications;

import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductsSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));

    }

    public static Specification<Product> belongsToCategory(List<Category> categories){
        return (root, query, criteriaBuilder)->
                criteriaBuilder.in(root.get("category")).value(categories);
    }
}
