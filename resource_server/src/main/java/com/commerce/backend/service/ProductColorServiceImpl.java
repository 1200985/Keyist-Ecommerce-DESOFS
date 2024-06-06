package com.commerce.backend.service;

import com.commerce.backend.converter.color.ProductColorResponseConverter;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Color;
import com.commerce.backend.model.response.color.ProductColorResponse;
import com.commerce.backend.service.cache.ProductColorCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductColorServiceImpl implements ProductColorService {

    private static final Logger logger = LoggerFactory.getLogger(ProductColorServiceImpl.class);

    private final ProductColorCacheService productColorCacheService;
    private final ProductColorResponseConverter productColorResponseConverter;

    @Autowired
    public ProductColorServiceImpl(ProductColorCacheService productColorCacheService, ProductColorResponseConverter productColorResponseConverter) {
        this.productColorCacheService = productColorCacheService;
        this.productColorResponseConverter = productColorResponseConverter;
    }

    @Override
    public List<ProductColorResponse> findAll() {
        logger.info("Fetching all product colors");
        List<Color> productColors = productColorCacheService.findAll();
        if (productColors.isEmpty()) {
            logger.error("No product colors found");
            throw new ResourceNotFoundException("Could not find product colors");
        }
        return productColors
                .stream()
                .map(productColorResponseConverter)
                .collect(Collectors.toList());
    }
}
