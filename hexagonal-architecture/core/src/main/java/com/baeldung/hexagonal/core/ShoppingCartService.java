package com.baeldung.hexagonal.core;

public class ShoppingCartService {

    private final ProductRepository productRepository;

    private final LoggedInCustomerHolder customerHolder;

    private final CartRepository cartRepository;

    private final CouponRepository couponRepository;

    public ShoppingCartService(ProductRepository productRepository, LoggedInCustomerHolder customerHolder,
            CartRepository cartRepository, CouponRepository couponRepository) {
        this.productRepository = productRepository;
        this.customerHolder = customerHolder;
        this.cartRepository = cartRepository;
        this.couponRepository = couponRepository;
    }

    public void addToCart(Long productId, int requestedQuantity) {
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        if (product.getStockQuantity() < requestedQuantity) {
            throw new OutOfStorageException(product.getStockQuantity(), requestedQuantity);
        }
        Cart cart = obtainCart();
        cart = cart.addItem(new CartItem(product, requestedQuantity));
        cartRepository.save(cart);
    }

    public Cart obtainCart() {
        Customer customer = customerHolder.getCustomer();
        return cartRepository.findCartOfCustomer(customer.getId()).orElseGet(() ->  new Cart(customer));
    }

    public void applyCouponCode(String couponCode) {
        Coupon coupon = couponRepository.findCouponByCode(couponCode).orElseThrow(() -> new CouponNotFoundException(couponCode));
        Cart cart = obtainCart().withAppliedCoupon(coupon);
        int effectivePrice = calculateEffectivePrice(cart);
        cart = cart.withEffectivePrice(effectivePrice);
        cartRepository.save(cart);
    }

    private Integer calculateEffectivePrice(Cart cart) {
        return cart.getAppliedCoupons().stream()
                .reduce(cart.calculateBasePrice(), this::calculateDiscount, (totalPrice, discount) -> totalPrice - discount);
    }

    private int calculateDiscount(int basePrice,  Coupon coupon) {
        if (coupon.isFixed()) {
            return coupon.getFixedDiscount();
        } else {
            return (int) (basePrice * ((coupon.getDiscountPercentage()) / 100.0));
        }
    }

}
