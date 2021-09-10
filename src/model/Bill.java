package model;

import java.time.LocalDate;

public class Bill extends Common{
    private LocalDate dateBillOut;
    private Cart cart;
    private User user;

    public Bill() {
    }

    public Bill(String id, String name, LocalDate dateBillOut, Cart cart) {
        super(id, name);
        this.dateBillOut = dateBillOut;
        this.cart = cart;
    }

    public Bill(String id, String name, LocalDate dateBillOut, User user) {
        super(id, name);
        this.dateBillOut = dateBillOut;
        this.user = user;
    }

    public LocalDate getDateBillOut() {
        return dateBillOut;
    }

    public void setDateBillOut(LocalDate dateBillOut) {
        this.dateBillOut = dateBillOut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "THÔNG TIN ĐƠN HÀNG\n- Mã đơn hàng : " + getId() +
                "\n- Tên đơn hàng : Bán sản phẩm " + cart.getProduct().getName() +
                "\n- Tên sản phẩm : " + cart.getProduct().getName() +
                "\n- Ngày xuất đơn : " + getDateBillOut() + "\n- Khách hàng : " + getUser().getFullName() +
                "\n- Địa chỉ : " + getUser().getAddress() + "\n- Số điện thoại : " + getUser().getNumberPhone() +
                "\n- Số lượng : " + cart.getQuantity() + "\n- Thành tiền : " + cart.realMoney() + " VNĐ" + "\n";
    }
}
