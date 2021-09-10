package controller;

import model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartManagement implements IGeneralManagement<Cart> {
    List<Cart> carts = new ArrayList<>();

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public void addNew(Cart cart) {
        carts.add(cart);
    }

    @Override
    public void delete(int index) {
        carts.remove(index);
    }

    @Override
    public void displayAll() {
        if (carts.size() == 0) {
            System.out.println("KHÔNG CÓ ĐƠN HÀNG NÀO TRONG GIỎ HÀNG!");
        } else {
            for (Cart cart : carts) {
                System.out.println(cart);
            }
        }
    }

    @Override
    public void update(int index, Cart cart) {
        carts.set(index, cart);
    }

    @Override
    public int findIndex(String productID) {
        int index = -1;
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getProduct().getId().equals(productID)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
