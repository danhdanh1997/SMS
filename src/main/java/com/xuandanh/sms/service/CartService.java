package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Cart;
import com.xuandanh.sms.domain.Customer;
import com.xuandanh.sms.domain.Product;
import com.xuandanh.sms.domain.User;
import com.xuandanh.sms.dto.cart.AddToCartDTO;
import com.xuandanh.sms.dto.cart.CartDTO;
import com.xuandanh.sms.dto.cart.CartItemDTO;
import com.xuandanh.sms.exception.CartItemNotExistException;
import com.xuandanh.sms.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    public void addToCart(AddToCartDTO addToCartDto, Product product, Customer customer){
        Cart cart = new Cart(product, addToCartDto.getQuantity(), customer);
        cartRepository.save(cart);
    }

    public static CartItemDTO getDtoFromCart(Cart cart) {
        return new CartItemDTO(cart);
    }

    public CartDTO listCartItems(Customer customer) {
        List<Cart> cartList = cartRepository.findAllByCustomerOrderByCreatedDate(customer);
        List<CartItemDTO> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDTO cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDTO cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getUnitPrice()* cartItemDto.getQuantity());
        }
        return new CartDTO(cartItems,totalCost);
    }

    public void updateCartItem(AddToCartDTO cartDto, Customer customer,Product product){
        Cart cart = cartRepository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(int id,String customerId) throws CartItemNotExistException {
        if (!cartRepository.existsById(Long.valueOf(customerId)))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(customerId);
    }

    public void deleteCartItems(int userId) {
        cartRepository.deleteAll();
    }


    public void deleteCustomerCartItems(Customer customer) {
        cartRepository.deleteByCustomer(customer);
    }
}
