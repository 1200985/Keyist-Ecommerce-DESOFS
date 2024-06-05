package com.commerce.backend.service;

import com.commerce.backend.converter.category.ProductCategoryResponseConverter;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.ProductCategory;
import com.commerce.backend.model.response.category.ProductCategoryResponse;
import com.commerce.backend.service.cache.ProductCategoryCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    private final ProductCategoryCacheService productCategoryCacheService;
    private final ProductCategoryResponseConverter productCategoryResponseConverter;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryCacheService productCategoryCacheService,
                                      ProductCategoryResponseConverter productCategoryResponseConverter) {
        this.productCategoryCacheService = productCategoryCacheService;
        this.productCategoryResponseConverter = productCategoryResponseConverter;
    }

    @Override
    public List<ProductCategoryResponse> findAllByOrderByName() {
        List<ProductCategory> productCategories = productCategoryCacheService.findAllByOrderByName();
        if (productCategories.isEmpty()) {
            logger.warn("Product categories not found");
            throw new ResourceNotFoundException("Could not find product categories");
        }
        logger.info("Product categories fetched: count={}", productCategories.size());
        return productCategories
                .stream()
                .map(productCategoryResponseConverter)
                .collect(Collectors.toList());
    }
}
