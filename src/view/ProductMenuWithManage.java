package view;

import controller.ProductManagement;
import model.Product;

import static view.UserMenu.*;
import static view.ProductMenuWithUser.*;

public class ProductMenuWithManage {
    public static final String PHONE = "Điện thoại";
    public static final String LAPTOP = "Laptop";
    public static final String TABLET = "Máy tính bảng";
    public static final String PRODUCT_PATH = "product.txt";

    public static ProductManagement productManagement = new ProductManagement();

    public void runProductMenuWithManage() {
        int choice;
        do {
            menu();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    addNewProductToList();
                    break;
                }
                case 2: {
                    deleteProduct();
                    break;
                }
                case 3: {
                    productManagement.displayAll();
                    break;
                }
                case 4: {
                    editProduct();
                    break;
                }
                case 5: {
                    displayTotalRevenue();
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("Số nhập vào không đúng. mời nhập lại");
                    choice = -1;
                    break;
                }
            }
        } while (choice == -1);
    }

    private void displayTotalRevenue() {
        long total = BillAndCart.billManagement.totalRevenue();
        System.out.println("Tổng doanh thu = " + total + " VNĐ");
    }

    private void editProduct() {
        String id = inputProductId();
        int index = productManagement.findIndex(id);
        if (index == -1) {
            System.out.println("Không thấy mã sản phẩm này");
        } else {
            Product product = createLaptopProduct();
            productManagement.update(index, product);
        }
        writeFileProduct();
    }

    private String inputProductId() {
        String id = "";
        do {
            System.out.println("Nhập mã sản phẩm : ");
            id = scanner.nextLine();
            if (id.equals("")) {
                System.out.println("BẠN CHƯA NHẬP VÀO MÃ SẢN PHẨM");
            }
        } while (id.equals(""));
        return id;
    }

    private void deleteProduct() {
        String id = inputProductId();
        int index = productManagement.findIndex(id);
        if (index == -1) {
            System.out.println("Không thấy mã sản phẩm này");
        } else {
            int choice;
            do {
                System.out.println(productManagement.getProducts().get(index));
                menuDeleteProduct();
                choice = getChoice();
                switch (choice) {
                    case 1: {
                        productManagement.delete(index);
                        break;
                    }
                    case 2: {
                        break;
                    }
                    default: {
                        System.out.println("Số bạn chọn không có trong menu");
                        choice = -1;
                        break;
                    }
                }
            } while (choice == -1);
        }
        writeFileProduct();
    }

    private void addNewProductToList() {
        int choice;
        do {
            menuAddNew();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    addNewPhone();
                    break;
                }
                case 2: {
                    addNewLaptop();
                    break;
                }
                case 3: {
                    addNewTablet();
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("Số nhập vào không hợp lệ mời nhập lại");
                }
            }
            writeFileProduct();
        } while (choice != 0);
    }

    public void readFileProduct() {
        productManagement.readFile(PRODUCT_PATH);
    }

    public void writeFileProduct() {
        productManagement.writeFile(PRODUCT_PATH);
    }

    private void addNewTablet() {
        Product product = createPhoneProduct();
        product.setType(TABLET);
        productManagement.addNew(product);
    }

    private void addNewLaptop() {
        Product product = createLaptopProduct();
        product.setType(LAPTOP);
        productManagement.addNew(product);
    }

    private void addNewPhone() {
        Product product = createPhoneProduct();
        product.setType(PHONE);
        productManagement.addNew(product);
    }

    private Product createLaptopProduct() {
        int index;
        String id;
        do {
            System.out.println("Mã sản phẩm : ");
            id = scanner.nextLine();
            index = productManagement.findIndex(id);
            if (index != -1) {
                System.out.println("Mã sản phẩm này đã tồn tại");
            }
        } while (index != -1);
        System.out.println("Tên sản phẩm : ");
        String name = scanner.nextLine();
        String company = inputCompanyLaptopProduct();
        int amount = inputAmount();
        long price = inputPrice();
        return new Product(id, name, company, amount, price);
    }

    private Product createPhoneProduct() {
        int index;
        String id;
        do {
            System.out.println("Mã sản phẩm : ");
            id = scanner.nextLine();
            index = productManagement.findIndex(id);
            if (index != -1) {
                System.out.println("Mã sản phẩm này đã tồn tại");
            }
        } while (index != -1);
        System.out.println("Tên sản phẩm : ");
        String name = scanner.nextLine();
        String company = inputCompanyPhoneProduct();
        int amount = inputAmount();
        long price = inputPrice();
        return new Product(id, name, company, amount, price);
    }

    private String inputCompanyPhoneProduct() {
        String company = "";
        int choice;
        do {
            menuCompanyPhoneProduct();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    company = SAMSUNG;
                    break;
                }
                case 2: {
                    company = APPLE;
                    break;
                }
                case 3: {
                    company = XIAOMI;
                    break;
                }
                case 4: {
                    company = OPPO;
                    break;
                }
                case 5: {
                    company = ASUS;
                    break;
                }
                default: {
                    System.out.println("Bạn chọn hãng không hợp lệ mời nhập lại");
                    break;
                }
            }
        } while (company.equals(""));
        return company;
    }

    private String inputCompanyLaptopProduct() {
        String company = "";
        int choice;
        do {
            menuCompanyLaptopProduct();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    company = SAMSUNG;
                    break;
                }
                case 2: {
                    company = APPLE;
                    break;
                }
                case 3: {
                    company = DELL;
                    break;
                }
                case 4: {
                    company = ASUS;
                    break;
                }
                case 5: {
                    company = ACER;
                    break;
                }
                case 6: {
                    company = HP;
                    break;
                }
                case 7: {
                    company = LENOVO;
                    break;
                }
                case 8: {
                    company = MSI;
                    break;
                }
                default: {
                    System.out.println("Bạn chọn hãng không hợp lệ mời nhập lại");
                    break;
                }
            }
        } while (company.equals(""));
        return company;
    }

    private void menuCompanyLaptopProduct() {
        System.out.println("HÃNG SẢN XUẤT");
        System.out.println("1. Samsung");
        System.out.println("2. Apple");
        System.out.println("3. Dell");
        System.out.println("4. Asus");
        System.out.println("5. Acer");
        System.out.println("6. HP");
        System.out.println("7. Lenovo");
        System.out.println("8. MSI");

    }

    private void menuCompanyPhoneProduct() {
        System.out.println("HÃNG SẢN XUẤT");
        System.out.println("1. Samsung");
        System.out.println("2. Apple");
        System.out.println("3. Xiaomi");
        System.out.println("4. Oppo");
        System.out.println("5. Asus");
    }

    private long inputPrice() {
        long price = -1;
        do {
            try {
                System.out.println("Giá sản phẩm : ");
                String inputPrice = scanner.nextLine();
                price = Long.parseLong(inputPrice);
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập vào dạng số");
            }
        } while (price == -1);
        return price;
    }

    private int inputAmount() {
        int amount = -1;
        do {
            try {
                System.out.println("Số lượng sản phẩm : ");
                String inputAmount = scanner.nextLine();
                amount = Integer.parseInt(inputAmount);
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập vào dạng số");
            }
        } while (amount == -1);
        return amount;
    }

    private void menuAddNew() {
        System.out.println("THÊM SẢN PHẨM");
        System.out.println("1. " + PHONE);
        System.out.println("2. " + LAPTOP);
        System.out.println("3. " + TABLET);
        System.out.println("0. Quay lại");
    }

    private void menu() {
        System.out.println("Tài khoản : ");
        System.out.println("QUẢN LÝ DANH MỤC SẢN PHẨM");
        System.out.println("1. Thêm một sản phẩm mới");
        System.out.println("2. Xóa sản phẩm");
        System.out.println("3. Xem toàn bộ sản phẩm");
        System.out.println("4. Sửa thông tin sản phẩm");
        System.out.println("5. Quản lý doanh thu");
        System.out.println("0. Quay lại");
    }

    private void menuDeleteProduct() {
        System.out.println("Bạn chắc chắn muốn xóa sản phẩm?");
        System.out.println("1. Xóa");
        System.out.println("2. Quay lại");
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