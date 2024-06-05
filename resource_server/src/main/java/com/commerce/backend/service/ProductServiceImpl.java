package com.commerce.backend.service;

import com.commerce.backend.converter.product.ProductDetailsResponseConverter;
import com.commerce.backend.converter.product.ProductResponseConverter;
import com.commerce.backend.converter.product.ProductVariantResponseConverter;
import com.commerce.backend.dao.ProductRepository;
import com.commerce.backend.dao.ProductVariantRepository;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Product;
import com.commerce.backend.model.entity.ProductVariant;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import com.commerce.backend.model.response.product.ProductResponse;
import com.commerce.backend.model.response.product.ProductVariantResponse;
import com.commerce.backend.model.specs.ProductVariantSpecs;
import com.commerce.backend.service.cache.ProductCacheService;
import com.commerce.backend.service.cache.ProductVariantCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductCacheService productCacheService;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantCacheService productVariantCacheService;
    private final ProductResponseConverter productResponseConverter;
    private final ProductVariantResponseConverter productVariantResponseConverter;
    private final ProductDetailsResponseConverter productDetailsResponseConverter;

    @Autowired
    public ProductServiceImpl(ProductCacheService productCacheService,
                              ProductRepository productRepository,
                              ProductVariantRepository productVariantRepository,
                              ProductVariantCacheService productVariantCacheService,
                              ProductResponseConverter productResponseConverter,
                              ProductVariantResponseConverter productVariantResponseConverter,
                              ProductDetailsResponseConverter productDetailsResponseConverter) {
        this.productCacheService = productCacheService;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.productVariantCacheService = productVariantCacheService;
        this.productResponseConverter = productResponseConverter;
        this.productVariantResponseConverter = productVariantResponseConverter;
        this.productDetailsResponseConverter = productDetailsResponseConverter;
    }

    @Override
    public ProductDetailsResponse findByUrl(String url) {
        logger.info("Fetching product by URL: {}", url);
        Product product = productCacheService.findByUrl(url);
        if (Objects.isNull(product)) {
            logger.error("Product not found with the URL: {}", url);
            throw new ResourceNotFoundException(String.format("Product not found with the URL %s", url));
        }
        return productDetailsResponseConverter.apply(product);
    }

    @Override
    public ProductVariant findProductVariantById(Long id) {
        logger.info("Fetching product variant by ID: {}", id);
        ProductVariant productVariant = productVariantCacheService.findById(id);
        if (Objects.isNull(productVariant)) {
            logger.error("Product variant not found with the ID: {}", id);
            throw new ResourceNotFoundException(String.format("Could not find any product variant with the ID %d", id));
        }
        return productVariant;
    }

    @Override
    public List<ProductVariantResponse> getAll(Integer page, Integer size, String sort, String category, Float minPrice, Float maxPrice, String color) {
        PageRequest pageRequest;
        if (Objects.nonNull(sort) && !sort.isBlank()) {
            Sort sortRequest = getSort(sort);
            if (Objects.isNull(sortRequest)) {
                logger.error("Invalid sort parameter: {}", sort);
                throw new InvalidArgumentException("Invalid sort parameter");
            }
            pageRequest = PageRequest.of(page, size, sortRequest);
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        logger.info("Fetching all product variants with filters - page: {}, size: {}, sort: {}, category: {}, minPrice: {}, maxPrice: {}, color: {}",
                page, size, sort, category, minPrice, maxPrice, color);

        Specification<ProductVariant> combinations =
                Objects.requireNonNull(Specification.where(ProductVariantSpecs.withColor(color)))
                        .and(ProductVariantSpecs.withCategory(category))
                        .and(ProductVariantSpecs.minPrice(minPrice))
                        .and(ProductVariantSpecs.maxPrice(maxPrice));

        return productVariantRepository.findAll(combinations, pageRequest)
                .stream()
                .map(productVariantResponseConverter)
                .collect(Collectors.toList());
    }

    @Override
    public Long getAllCount(String category, Float minPrice, Float maxPrice, String color) {
        logger.info("Fetching count of all product variants with filters - category: {}, minPrice: {}, maxPrice: {}, color: {}",
                category, minPrice, maxPrice, color);

        Specification<ProductVariant> combinations =
                Objects.requireNonNull(Specification.where(ProductVariantSpecs.withColor(color)))
                        .and(ProductVariantSpecs.withCategory(category))
                        .and(ProductVariantSpecs.minPrice(minPrice))
                        .and(ProductVariantSpecs.maxPrice(maxPrice));

        return productVariantRepository.count(combinations);
    }

    @Override
    public List<ProductResponse> getRelatedProducts(String url) {
        logger.info("Fetching related products for URL: {}", url);
        Product product = productCacheService.findByUrl(url);
        if (Objects.isNull(product)) {
            logger.error("Related products not found for URL: {}", url);
            throw new ResourceNotFoundException("Related products not found");
        }
        List<Product> products = productCacheService.getRelatedProducts(product.getProductCategory(), product.getId());
        return products
                .stream()
                .map(productResponseConverter)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getNewlyAddedProducts() {
        logger.info("Fetching newly added products");
        List<Product> products = productCacheService.findTop8ByOrderByDateCreatedDesc();
        if (products.isEmpty()) {
            logger.error("Newly added products not found");
            throw new ResourceNotFoundException("Newly added products not found");
        }
        return products
                .stream()
                .map(productResponseConverter)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVariantResponse> getMostSelling() {
        logger.info("Fetching most selling products");
        List<ProductVariant> productVariants = productVariantCacheService.findTop8ByOrderBySellCountDesc();
        if (productVariants.isEmpty()) {
            logger.error("Most selling products not found");
            throw new ResourceNotFoundException("Most selling products not found");
        }

        return productVariants
                .stream()
                .map(productVariantResponseConverter)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getInterested() {
        logger.info("Fetching interested products");
        List<Product> products = productCacheService.findTop8ByOrderByDateCreatedDesc();
        if (products.isEmpty()) {
            logger.error("Interested products not found");
            throw new ResourceNotFoundException("Interested products not found");
        }
        return products
                .stream()
                .map(productResponseConverter)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProductDisplay(String keyword, Integer page, Integer size) {
        logger.info("Searching products with keyword: {}, page: {}, size: {}", keyword, page, size);
        if (Objects.isNull(page) || Objects.isNull(size)) {
            logger.error("Page and size are required");
            throw new InvalidArgumentException("Page and size are required");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Product> products = productRepository.findAllByNameContainingIgnoreCase(keyword, pageRequest);
        return products
                .stream()
                .map(productResponseConverter)
                .collect(Collectors.toList());
    }

    private Sort getSort(String sort) {
        switch (sort) {
            case "lowest":
                return Sort.by(Sort.Direction.ASC, "price");
            case "highest":
                return Sort.by(Sort.Direction.DESC, "price");
            default:
                return null;
        }
    }
}
