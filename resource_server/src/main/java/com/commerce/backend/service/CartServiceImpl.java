package com.commerce.backend.service;

import com.commerce.backend.converter.cart.CartResponseConverter;
import com.commerce.backend.dao.CartRepository;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.dto.CartItemDTO;
import com.commerce.backend.model.entity.Cart;
import com.commerce.backend.model.entity.CartItem;
import com.commerce.backend.model.entity.ProductVariant;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.request.cart.ConfirmCartRequest;
import com.commerce.backend.model.response.cart.CartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CartResponseConverter cartResponseConverter;
    

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
            ProductService productService,
            UserService userService,
            CartResponseConverter cartResponseConverter) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
        this.cartResponseConverter = cartResponseConverter;
    }

    @Override
    public CartResponse addToCart(Long productVariantId, Integer amount) {
        logger.info("Adding to cart: productVariantId={}, amount={}", productVariantId, amount);
        User user = userService.getUser();
        Cart cart = user.getCart();

        if (Objects.nonNull(cart) && Objects.nonNull(cart.getCartItemList()) && !cart.getCartItemList().isEmpty()) {
            Optional<CartItem> cartItem = cart.getCartItemList()
                    .stream()
                    .filter(ci -> ci.getProductVariant().getId().equals(productVariantId)).findFirst();
            if (cartItem.isPresent()) {
                if (cartItem.get().getProductVariant().getStock() < (cartItem.get().getAmount() + amount)) {
                    logger.warn("Product does not have desired stock: productVariantId={}, availableStock={}, requestedAmount={}", 
                                productVariantId, cartItem.get().getProductVariant().getStock(), amount);
                    throw new InvalidArgumentException("Product does not have desired stock.");
                }
                cartItem.get().setAmount(cartItem.get().getAmount() + amount);
                Cart updatedCart = calculatePrice(cart);
                cart = cartRepository.save(updatedCart);
                logger.info("Product added to existing cart item: productVariantId={}, newAmount={}", 
                            productVariantId, cartItem.get().getAmount());
                return cartResponseConverter.apply(cart);
            }
        }

        if (Objects.isNull(cart)) {
            cart = createCart(user);
            logger.info("New cart created for user: userId={}", user.getId());
        }

        ProductVariant productVariant = productService.findProductVariantById(productVariantId);

        if (productVariant.getStock() < amount) {
            logger.warn("Product does not have desired stock: productVariantId={}, availableStock={}, requestedAmount={}", 
                        productVariantId, productVariant.getStock(), amount);
            throw new InvalidArgumentException("Product does not have desired stock.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setAmount(amount);
        cartItem.setProductVariant(productVariant);
        cartItem.setCart(cart);

        if (Objects.isNull(cart.getCartItemList())) {
            cart.setCartItemList(new ArrayList<>());
        }
        cart.getCartItemList().add(cartItem);
        cart = calculatePrice(cart);

        cart = cartRepository.save(cart);
        logger.info("Product added to cart: productVariantId={}, amount={}", productVariantId, amount);
        return cartResponseConverter.apply(cart);
    }

    @Override
    public CartResponse incrementCartItem(Long cartItemId, Integer amount) {
        logger.info("Incrementing cart item: cartItemId={}, amount={}", cartItemId, amount);
        User user = userService.getUser();
        Cart cart = user.getCart();
        if (Objects.isNull(cart) || Objects.isNull(cart.getCartItemList()) || cart.getCartItemList().isEmpty()) {
            logger.warn("Attempted to increment cart item in an empty cart: cartItemId={}", cartItemId);
            throw new ResourceNotFoundException("Empty cart");
        }

        CartItem cartItem = cart.getCartItemList()
                .stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("CartItem not found: cartItemId={}", cartItemId);
                    return new ResourceNotFoundException("CartItem not found");
                });

        if (cartItem.getProductVariant().getStock() < (cartItem.getAmount() + amount)) {
            logger.warn("Product does not have desired stock: productVariantId={}, availableStock={}, requestedAmount={}", 
                        cartItem.getProductVariant().getId(), cartItem.getProductVariant().getStock(), amount);
            throw new InvalidArgumentException("Product does not have desired stock.");
        }

        cartItem.setAmount(cartItem.getAmount() + amount);
        cart = calculatePrice(cart);
        cart = cartRepository.save(cart);
        logger.info("Cart item incremented: cartItemId={}, newAmount={}", cartItemId, cartItem.getAmount());
        return cartResponseConverter.apply(cart);
    }

    @Override
    public CartResponse decrementCartItem(Long cartItemId, Integer amount) {
        logger.info("Decrementing cart item: cartItemId={}, amount={}", cartItemId, amount);
        User user = userService.getUser();
        Cart cart = user.getCart();
        if (Objects.isNull(cart) || Objects.isNull(cart.getCartItemList()) || cart.getCartItemList().isEmpty()) {
            logger.warn("Attempted to decrement cart item in an empty cart: cartItemId={}", cartItemId);
            throw new ResourceNotFoundException("Empty cart");
        }

        CartItem cartItem = cart.getCartItemList()
                .stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("CartItem not found: cartItemId={}", cartItemId);
                    return new ResourceNotFoundException("CartItem not found");
                });

        if (cartItem.getAmount() <= amount) {
            List<CartItem> cartItemList = cart.getCartItemList();
            cartItemList.remove(cartItem);
            if (Objects.isNull(cart.getCartItemList()) || cart.getCartItemList().isEmpty()) {
                user.setCart(null);
                userService.saveUser(user);
                logger.info("Cart emptied for user: userId={}", user.getId());
                return null;
            }
            cart.setCartItemList(cartItemList);
            cart = calculatePrice(cart);
            cart = cartRepository.save(cart);
            logger.info("Cart item removed from cart: cartItemId={}", cartItemId);
            return cartResponseConverter.apply(cart);
        }

        cartItem.setAmount(cartItem.getAmount() - amount);
        cart = calculatePrice(cart);
        cart = cartRepository.save(cart);
        logger.info("Cart item decremented: cartItemId={}, newAmount={}", cartItemId, cartItem.getAmount());
        return cartResponseConverter.apply(cart);
    }

    @Override
    public CartResponse fetchCart() {
        logger.info("Fetching cart for current user");
        Cart cart = userService.getUser().getCart();
        if (cart == null) {
            logger.warn("Cart not found for current user");
            return null;
        }
        return cartResponseConverter.apply(cart);
    }

    @Override
    public CartResponse removeFromCart(Long cartItemId) {
        logger.info("Removing from cart: cartItemId={}", cartItemId);
        User user = userService.getUser();
        Cart cart = user.getCart();

        if (Objects.isNull(cart) || Objects.isNull(cart.getCartItemList()) || cart.getCartItemList().isEmpty()) {
            logger.warn("Cart or CartItem not found for removal: cartItemId={}", cartItemId);
            throw new ResourceNotFoundException("Cart or CartItem not found");
        }

        List<CartItem> cartItemsList = cart.getCartItemList();
        Optional<CartItem> cartItem = cart.getCartItemList()
                .stream()
                .filter(ci -> ci.getId().equals(cartItemId)).findFirst();
        if (cartItem.isEmpty()) {
            logger.warn("CartItem not found for removal: cartItemId={}", cartItemId);
            throw new ResourceNotFoundException("CartItem not found");
        }

        cartItemsList.remove(cartItem.get());

        if (Objects.isNull(cart.getCartItemList()) || cart.getCartItemList().isEmpty()) {
            user.setCart(null);
            userService.saveUser(user);
            logger.info("Cart emptied for user: userId={}", user.getId());
            return null;
        }

        cart.setCartItemList(cartItemsList);
        cart = calculatePrice(cart);
        cart = cartRepository.save(cart);
        logger.info("Cart item removed: cartItemId={}", cartItemId);
        return cartResponseConverter.apply(cart);
    }

    @Override
    public boolean confirmCart(ConfirmCartRequest confirmCartRequest) {
        logger.info("Confirming cart for current user");
        Cart dbCart = userService.getUser().getCart();

            // Introduce a delay of 2 seconds (2000 milliseconds)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted", e);
            Thread.currentThread().interrupt();
        }

        if (Objects.isNull(dbCart)) {
            logger.warn("Cart not found for confirmation");
            return false;
        }
        List<CartItem> dbCartItemsList = dbCart.getCartItemList();
        List<CartItemDTO> cartItemsList = confirmCartRequest.getCartItems();
        if (dbCartItemsList.size() != cartItemsList.size()) {
            logger.warn("Cart item count mismatch during confirmation");
            return false;
        }

        for (int i = 0; i < dbCartItemsList.size(); i++) {
            if (!dbCartItemsList.get(i).getId().equals(cartItemsList.get(i).getId()) &&
                    !dbCartItemsList.get(i).getAmount().equals(cartItemsList.get(i).getAmount()) &&
                    !dbCartItemsList.get(i).getProductVariant().getId().equals(cartItemsList.get(i).getId())) {
                logger.warn("Cart item details mismatch during confirmation");
                return false;
            }
        }

        if (dbCart.getTotalPrice().equals(confirmCartRequest.getTotalPrice()) &&
                dbCart.getTotalCargoPrice().equals(confirmCartRequest.getTotalCargoPrice()) &&
                dbCart.getTotalCartPrice().equals(confirmCartRequest.getTotalCartPrice())) {
            if (Objects.nonNull(dbCart.getDiscount()) && Objects.nonNull(confirmCartRequest.getDiscount())) {
                boolean discountMatches = dbCart.getDiscount().getDiscountPercent()
                        .equals(confirmCartRequest.getDiscount().getDiscountPercent());
                logger.info("Cart confirmation result: {}", discountMatches);
                return discountMatches;
            }
            boolean noDiscountMismatch = Objects.isNull(dbCart.getDiscount()) && Objects.isNull(confirmCartRequest.getDiscount());
            logger.info("Cart confirmation result: {}", noDiscountMismatch);
            return noDiscountMismatch;
        }
        logger.warn("Total price mismatch during cart confirmation");
        return false;
    }

    @Override
    public void emptyCart() {
        logger.info("Emptying cart for current user");
        User user = userService.getUser();
        user.setCart(null);
        userService.saveUser(user);
        logger.info("Cart emptied for user: userId={}", user.getId());
    }

    @Override
    public Cart getCart() {
        logger.info("Getting cart for current user");
        return userService.getUser().getCart();
    }

    @Override
    public void saveCart(Cart cart) {
        if (Objects.isNull(cart)) {
            logger.warn("Attempted to save a null cart");
            throw new InvalidArgumentException("Cart is null");
        }
        cartRepository.save(cart);
        logger.info("Cart saved: cartId={}", cart.getId());
    }

    @Override
    public Cart calculatePrice(Cart cart) {
        logger.info("Calculating prices for cart: cartId={}", cart.getId());
        cart.setTotalCartPrice(0F);
        cart.setTotalCargoPrice(0F);
        cart.setTotalPrice(0F);

        cart.getCartItemList().forEach(cartItem -> {
            cart.setTotalCartPrice(
                    cart.getTotalCartPrice() + (cartItem.getProductVariant().getPrice()) * cartItem.getAmount());
            cart.setTotalCargoPrice(
                    cart.getTotalCargoPrice() + (cartItem.getProductVariant().getCargoPrice()) * cartItem.getAmount());
            cart.setTotalPrice(
                    cart.getTotalPrice() +
                            (cartItem.getProductVariant().getPrice() + cartItem.getProductVariant().getCargoPrice())
                                    * cartItem.getAmount());
        });

        if (Objects.nonNull(cart.getDiscount())) {
            cart.setTotalPrice(
                    cart.getTotalPrice() - ((cart.getTotalPrice() * cart.getDiscount().getDiscountPercent()) / 100));
        }

        cart.setTotalPrice(roundTwoDecimals(cart.getTotalPrice()));
        cart.setTotalCargoPrice(roundTwoDecimals(cart.getTotalCargoPrice()));
        logger.info("Prices calculated for cart: cartId={}, totalCartPrice={}, totalCargoPrice={}, totalPrice={}", 
                    cart.getId(), cart.getTotalCartPrice(), cart.getTotalCargoPrice(), cart.getTotalPrice());
        return cart;
    }

    private float roundTwoDecimals(float d) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat twoDForm = new DecimalFormat("#.##", symbols);
        return Float.parseFloat(twoDForm.format(d));
    }

    private Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        logger.info("Created new cart for user: userId={}", user.getId());
        return cart;
    }
}
