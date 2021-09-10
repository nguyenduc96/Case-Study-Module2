package view;

import model.Bill;
import model.Cart;
import model.Product;
import model.User;

import java.util.List;

import static view.BillAndCart.billManagement;
import static view.BillAndCart.cartManagement;
import static view.ProductMenuWithManage.*;
import static view.UserMenu.*;

public class ProductMenuWithUser {

    public static final String SAMSUNG = "Samsung";
    public static final String APPLE = "Apple";
    public static final String XIAOMI = "Xiaomi";
    public static final String OPPO = "Oppo";
    public static final String DELL = "Dell";
    public static final String ASUS = "Asus";
    public static final String ACER = "Acer";
    public static final String HP = "HP";
    public static final String LENOVO = "Lenovo";
    public static final String MSI = "MSI";
    public static final String BILLS_PATH = "bills.txt";
    public static BillAndCart billAndCart = new BillAndCart();

    public void runProductMenuWithUser(User user) {
        int choice;
        do {
            menu();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    displayWithCompany();
                    break;
                }
                case 2: {
                    displayWithType();
                    break;
                }
                case 3: {
                    productManagement.displayAll();
                    break;
                }
                case 4: {
                    displayWithPrice();
                    break;
                }
                case 5: {
                    searchProductWithName();
                    break;
                }
                case 6: {
                    manageCart(user);
                    break;
                }
                case 7: {
                    manageBill(user);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ NHẬP KHÔNG ĐÚNG MỜI NHẬP LẠI");
                }
            }
        } while (choice != 0);
    }

    private void manageBill(User user) {
        int choice;
        do {
            menuBill();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    buyAllProduct(user);
                    user.getCarts().clear();
                    break;
                }
                case 2: {
                    buyOneProductFromCart(user);
                    break;
                }
                case 3: {
                    displayCartWithUser(user);
                    break;
                }
                case 4: {
                    displayBillWithUser(user);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ BẠN NHẬP KHÔNG CÓ TRONG MENU! MỜI NHẬP LẠi");
                }
            }
            writeBillsToFile();
            writeFile();
        } while (choice != 0);
    }

    private void displayBillWithUser(User user) {
        List<Bill> bills = user.getBills();
        if (bills == null) {
            System.out.println("CHƯA CÓ ĐƠN HÀNG NÀO");
        } else {
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }
    }

    private void displayCartWithUser(User user) {
        List<Cart> carts = user.getCarts();
        if (carts == null || carts.size() == 0) {
            System.out.println("KHÔNG CÓ ĐƠN HÀNG NÀO TRONG GIỎ");
        } else {
            for (Cart cart : carts) {
                System.out.println(cart);
            }
        }
    }

    private void manageCart(User user) {
        int choice;
        do {
            menuCart();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    displayCartWithUser(user);
                    break;
                }
                case 2: {
                    deleteCart(user);
                    break;
                }
                case 3: {
                    addProductToCart(user);
                    break;
                }
                case 4: {
                    updateCartInfo(user);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ CHỌN KHÔNG CÓ TRONG MENU");
                    break;
                }
            }
            writeFile();
        } while (choice != 0);
    }

    private void updateCartInfo(User user) {
        List<Cart> carts = user.getCarts();
        if (carts == null || carts.size() == 0) {
            System.out.println("Giỏ hàng đang trống không có gì chỉnh sửa");
        } else {
            System.out.println("Nhập mã sản phẩm của đơn hàng cần sửa : ");
            String id = scanner.nextLine();
            int indexInListCart = cartManagement.findIndex(id);
            if (indexInListCart != -1) {
                Cart cart = cartManagement.getCarts().get(indexInListCart);
                int indexCartWithUser = carts.indexOf(cart);
                if (indexCartWithUser == -1) {
                    System.out.println("KHÔNG CÓ ĐƠN HÀNG NÀY");
                } else {
                    System.out.println("CHỈNH SỬA ĐƠN HÀNG");
                    Cart cartEdit = billAndCart.editCartInfo();
                    carts.set(indexCartWithUser, cartEdit);
                }
            } else {
                System.out.println("KHÔNG THẤY SẢN PHẨM NÀY");
            }
        }
    }

    private void deleteCart(User user) {
        List<Cart> carts = user.getCarts();
        if (carts == null || carts.size() == 0) {
            System.out.println("Giỏ hàng đang trống không có gì để xóa");
        } else {
            System.out.println("Nhập mã sản phẩm của đơn hàng cần xóa : ");
            String id = scanner.nextLine();
            int indexInListCart = cartManagement.findIndex(id);
            if (indexInListCart != -1) {
                Cart cart = cartManagement.getCarts().get(indexInListCart);
                int indexCartWithUser = carts.indexOf(cart);
                if (indexCartWithUser == -1) {
                    System.out.println("KHÔNG CÓ ĐƠN HÀNG NÀY");
                } else {
                    carts.remove(indexCartWithUser);
                    System.out.println("ĐÃ XÓA");
                }
            } else {
                System.out.println("KHÔNG THẤY SẢN PHẨM NÀY");
            }
        }
    }

    public void readFileToBills() {
        billManagement.readFile(BILLS_PATH);
    }

    public void writeBillsToFile() {
        billManagement.writeFile(BILLS_PATH);
    }

    public void writeFile() {
        userManagement.writeFile(USER_PATH);
    }

    private void buyOneProductFromCart(User user) {
        try {
            billAndCart.createBillBuyOne(user);
            List<Bill> bills = billManagement.getBills();
            user.setBills(bills);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.fillInStackTrace();
        }
    }

    private void buyAllProduct(User user) {
        try {
            billAndCart.createBillAllCart(user);
            List<Bill> bills = billManagement.getBills();
            user.setBills(bills);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.fillInStackTrace();
        }
    }

    public void addProductToCart(User user) {
        try {
            billAndCart.addCartToList(user);
            List<Cart> carts = cartManagement.getCarts();
            user.setCarts(carts);
            System.out.println("THÊM VÀO GIỎ HÀNG THÀNH CÔNG");
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.fillInStackTrace();
        }
    }

    private void searchProductWithName() {
        System.out.println("TÌM KIẾM SẢN PHẨM");
        System.out.println("Nhập vào tên sản phẩm : ");
        String name = scanner.nextLine();
        boolean checkFindName = checkFindProductWitName(name);
        if (checkFindName) {
            for (Product product : productManagement.getProducts()) {
                System.out.println(product);
            }
        } else {
            System.out.println("KHÔNG TÌM THẤY SẢN PHẨM NÀO CÓ TÊN TƯƠNG TỰ " + name);
        }
    }

    private boolean checkFindProductWitName(String name) {
        for (Product product : productManagement.getProducts()) {
            boolean check = checkStringFind(product.getName(), name);
            if (check) {
                return true;
            }
        }
        return false;
    }

    private boolean checkStringFind(String stringFind, String name) {
        String[] strings = stringFind.split("");
        String[] stringName = name.split("");
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < stringName.length; j++) {
                if (strings[i].equals(stringName[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void displayWithPrice() {
        int choice;
        do {
            subMenuPriceProduct();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    sortBigToSmall();
                    break;
                }
                case 2: {
                    sortSmallToBig();
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ NHẬP KHÔNG ĐÚNG MỜI NHẬP LẠI");
                    break;
                }
            }
        } while (choice != 0);
    }

    private void sortSmallToBig() {
        productManagement.sortSmallToBigPrice();
        productManagement.displayAll();
    }

    private void sortBigToSmall() {
        productManagement.sortBigToSmallPrice();
        productManagement.displayAll();
    }

    private void subMenuPriceProduct() {
        System.out.println("XEM THEO GIÁ");
        System.out.println("1. Từ cao đến thấp");
        System.out.println("2. Từ thấp đến cao");
        System.out.println("0. Quay lại");
    }

    private void displayWithType() {
        int choice;
        do {
            subMenuTypeProduct();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    productManagement.displayWithTypeProduct(PHONE);
                    break;
                }
                case 2: {
                    productManagement.displayWithTypeProduct(LAPTOP);
                    break;
                }
                case 3: {
                    productManagement.displayWithTypeProduct(TABLET);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ NHẬP KHÔNG ĐÚNG MỜI NHẬP LẠI");
                    break;
                }
            }
        } while (choice != 0);
    }

    private void displayWithCompany() {
        int choice;
        do {
            subMenuCompany();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    productManagement.displayWithCompanyProduct(SAMSUNG);
                    break;
                }
                case 2: {
                    productManagement.displayWithCompanyProduct(APPLE);
                    break;
                }
                case 3: {
                    productManagement.displayWithCompanyProduct(XIAOMI);
                    break;
                }
                case 4: {
                    productManagement.displayWithCompanyProduct(OPPO);
                    break;
                }
                case 5: {
                    productManagement.displayWithCompanyProduct(DELL);
                    break;
                }
                case 6: {
                    productManagement.displayWithCompanyProduct(ASUS);
                    break;
                }
                case 7: {
                    productManagement.displayWithCompanyProduct(ACER);
                    break;
                }
                case 8: {
                    productManagement.displayWithCompanyProduct(HP);
                    break;
                }
                case 9: {
                    productManagement.displayWithCompanyProduct(LENOVO);
                    break;
                }
                case 10: {
                    productManagement.displayWithCompanyProduct(MSI);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ NHẬP KHÔNG ĐÚNG MỜI NHẬP LẠI");
                    break;
                }
            }
        } while (choice != 0);
    }

    private void subMenuCompany() {
        System.out.println("HÃNG SẢN XUẤT");
        System.out.println("1. Samsung");
        System.out.println("2. Apple");
        System.out.println("3. Xiaomi");
        System.out.println("4. Oppo");
        System.out.println("5. Dell");
        System.out.println("6. Asus");
        System.out.println("7. Acer");
        System.out.println("8. HP");
        System.out.println("9. Lenovo");
        System.out.println("10. MSI");
        System.out.println("0. Quay lại");
    }

    private void menu() {
        System.out.println("DANH MỤC SẢN PHẨM");
        System.out.println("1. Xem theo hãng sản xuất");
        System.out.println("2. Xem theo loại hàng");
        System.out.println("3. Xem toàn bộ sản phẩm");
        System.out.println("4. Xem theo giá sản phẩm");
        System.out.println("5. Tìm kiếm sản phẩm");
        System.out.println("6. Giỏ hàng");
        System.out.println("7. Mua hàng");
        System.out.println("0. Quay lại");
    }

    private void subMenuTypeProduct() {
        System.out.println("LOẠI MẶT HÀNG");
        System.out.println("1. Điện thoại");
        System.out.println("2. Laptop");
        System.out.println("3. Máy tính bảng");
        System.out.println("0. Quay lại");
    }

    private void menuBill() {
        System.out.println("QUẢN LÝ HÓA ĐƠN");
        System.out.println("1. Mua tất cả trong giỏ hàng");
        System.out.println("2. Mua 1 sản phẩm trong giỏ hàng");
        System.out.println("3. Xem đơn hàng trong giỏ hàng");
        System.out.println("4. Hiển thị các đơn hàng đã mua");
        System.out.println("0. Quay lại");
    }

    private void menuCart() {
        System.out.println("QUẢN LÝ GIỎ HÀNG");
        System.out.println("1. Xem giỏ hàng");
        System.out.println("2. Xóa sản phẩm khỏi giỏ hàng");
        System.out.println("3. Thêm sản phẩm vào giỏ hàng");
        System.out.println("4. Sửa thông tin đơn hàng");
        System.out.println("0. Quay lại");
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
