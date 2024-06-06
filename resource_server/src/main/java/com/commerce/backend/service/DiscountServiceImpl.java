package com.commerce.backend.service;

import com.commerce.backend.dao.DiscountRepository;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Cart;
import com.commerce.backend.model.entity.Discount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountRepository discountRepository;
    private final CartService cartService;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository, CartService cartService) {
        this.discountRepository = discountRepository;
        this.cartService = cartService;
    }

    @Override
    public void applyDiscount(String code) {
        logger.info("Applying discount with code: {}", code);
        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> {
                    logger.warn("Discount code not found: {}", code);
                    return new ResourceNotFoundException("Discount code not found");
                });

        if (discount.getStatus() != 1) {
            logger.warn("Discount code is expired: {}", code);
            throw new InvalidArgumentException("Discount code is expired!");
        }

        Cart cart = cartService.getCart();
        cart.setDiscount(discount);
        cart = cartService.calculatePrice(cart);
        cartService.saveCart(cart);
        logger.info("Discount applied: code={}, discountPercent={}", code, discount.getDiscountPercent());
    }
}
