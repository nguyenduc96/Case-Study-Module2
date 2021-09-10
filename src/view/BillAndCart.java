package view;

import controller.BillManagement;
import controller.CartManagement;
import model.Bill;
import model.Cart;
import model.Product;
import model.User;

import java.time.LocalDate;
import java.util.List;

import static view.ProductMenuWithManage.productManagement;
import static view.UserMenu.productMenuWithManage;
import static view.UserMenu.scanner;

public class BillAndCart {
    public static BillManagement billManagement = new BillManagement();
    public static CartManagement cartManagement = new CartManagement();

    public Cart createCart(User user) {
        String productID;
        int index;
        boolean checkCart;
        List<Cart> carts = user.getCarts();
        do {
            if (carts != null) {
                System.out.println("Nhập mã sản phẩm : ");
                productID = scanner.nextLine();
                checkCart = checkDuplicateCart(carts, productID);
                index = productManagement.findIndex(productID);
                if (index == -1) {
                    System.out.println("Không có sản phẩm này. Mời nhập lại");
                }
                if (checkCart) {
                    System.out.println("Bạn đã có sản phẩm này trong giỏ hàng! Vui lòng thanh toán rồi đặt tiếp");
                    return null;
                }
            } else {
                System.out.println("Nhập mã sản phẩm : ");
                productID = scanner.nextLine();
                index = productManagement.findIndex(productID);
                if (index == -1) {
                    System.out.println("Không có sản phẩm này. Mời nhập lại");
                }
            }
        } while (productID.equals("") || index == -1);
        Product product = productManagement.getProducts().get(index);
        int quantity = inputQuantity();
        return new Cart(product, quantity);
    }

    public Cart editCartInfo() {
        String productID;
        int index;
        do {
            System.out.println("Nhập mã sản phẩm mới : ");
            productID = scanner.nextLine();
            index = productManagement.findIndex(productID);
            if (index == -1) {
                System.out.println("Không có sản phẩm này. Mời nhập lại");
            }
        } while (productID.equals("") || index == -1);
        Product product = productManagement.getProducts().get(index);
        int quantity = inputQuantity();
        return new Cart(product, quantity);
    }

    public void addCartToList(User user) {
        Cart cart = createCart(user);
        cartManagement.addNew(cart);
    }

    public boolean checkDuplicateCart(List<Cart> carts, String id) {
        for (Cart cart : carts) {
            if (cart.getProduct().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private int inputQuantity() {
        int quantity = -1;
        do {
            try {
                System.out.println("Nhập vào số lượng sản phẩm : ");
                String inputQuantity = scanner.nextLine();
                quantity = Integer.parseInt(inputQuantity);
                if (quantity <= 0) {
                    System.out.println("Bạn phải nhập vào số lớn hơn 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập vào dạng số");
            }
        } while (quantity <= 0);
        return quantity;
    }

    public void createBillBuyOne(User user) {
        List<Cart> carts = user.getCarts();
        Bill bill;
        if (carts != null) {
            System.out.println("Nhập mã sản phẩm có trong giỏ hàng : ");
            String productId = scanner.nextLine();
            int index = cartManagement.findIndex(productId);
            if (index == -1) {
                System.out.println("Trong giỏ hàng không có sản phẩm này");
            } else {
                LocalDate localDate = LocalDate.now();
                String id = "00" + (billManagement.getBills().size() + 1);
                Cart cart = carts.get(index);
                Product product = cart.getProduct();
                int checkAmountProduct = product.getAmount() - cart.getQuantity();
                if (product.getAmount() <= 0) {
                    System.out.println("Sản phẩm đang hết hàng. Quý khách vui lòng chọn mua mặt hàng khác");
                    return;
                }
                if (checkAmountProduct < 0) {
                    System.out.println("Hiện tại không đủ hàng. Quý khách vui lòng chọn mua mặt hàng khác");
                    return;
                } else {
                    product.setAmount(checkAmountProduct);
                    bill = new Bill(id, product.getName(), localDate, cart);
                    bill.setUser(user);
                    billManagement.addNew(bill);
                    user.getCarts().remove(cart);
                    productMenuWithManage.writeFileProduct();
                }
            }
        } else {
            System.out.println("Trong giỏ hàng chưa có sản phẩm nào! Hãy thêm sản phẩm vào giỏ hàng");
        }
    }

    public void createBillAllCart(User user) {
        List<Cart> carts = user.getCarts();
        Bill bill;
        if (carts != null) {
            for (Cart cart : carts) {
                LocalDate localDate = LocalDate.now();
                String id = "00" + (billManagement.getBills().size() + 1);
                Product product = cart.getProduct();
                int checkAmountProduct = product.getAmount() - cart.getQuantity();
                if (product.getAmount() <= 0) {
                    System.out.println("Sản phẩm đang hết hàng. Quý khách vui lòng chọn mua mặt hàng khác");
                    return;
                }
                if (checkAmountProduct < 0) {
                    System.out.println("Hiện tại không đủ hàng. Quý khách vui lòng chọn mua mặt hàng khác");
                    return;
                } else {
                    product.setAmount(checkAmountProduct);
                    bill = new Bill(id, product.getName(), localDate, cart);
                    bill.setUser(user);
                    billManagement.addNew(bill);
                    productMenuWithManage.writeFileProduct();
                }
            }
        } else {
            System.out.println("Trong giỏ hàng chưa có sản phẩm nào! Hãy thêm sản phẩm vào giỏ hàng");
        }
    }

    private int getChoice() {
        int choice = -1;
        try {
            System.out.println("Mời bạn chọn : ");
            String inputChoice = scanner.nextLine();
            choice = Integer.parseInt(inputChoice);
        } catch (NumberFormatException e) {
            System.out.println("HÃY NHẬP VÀO DẠNG SỐ");
        }
        return choice;
    }
}
