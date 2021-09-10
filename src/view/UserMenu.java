package view;

import controller.UserManagement;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMenu {
    public static final String USER_PATH = "user.txt";
    public static Scanner scanner = new Scanner(System.in);
    public static UserManagement userManagement = new UserManagement();
    public static final String CUSTOMER = "Khách hàng";
    public static final String ADMIN = "Quản lý";
    public static final String BLOCK = "Khóa";
    public static final String ACTIVE = "Hoạt động";

    public static ProductMenuWithUser productMenuWithUser = new ProductMenuWithUser();
    public static ProductMenuWithManage productMenuWithManage = new ProductMenuWithManage();

    public void runUserMenu() {
        readFile();
        productMenuWithManage.readFileProduct();
        productMenuWithUser.readFileToBills();
        int choice;
        do {
            menu();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    loginAccount();
                    break;
                }
                case 2: {
                    registerAccount();
                    break;
                }
                case 0: {
                    System.exit(0);
                }
                default: {
                    System.out.println("SỐ BẠN NHẬP KHÔNG ĐÚNG TRONG MENU");
                }
            }
        } while (choice != 0);
    }

    private void changeAccountInfo(User user) {
        int index = userManagement.findIndex(user.getUseName());
        int choice;
        do {
            subMenu();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    setPasswordAccount(user, index);
                    break;
                }
                case 2: {
                    setFullNameAccount(user, index);
                    break;
                }
                case 3: {
                    setEmailAccount(user, index);
                    break;
                }
                case 4: {
                    setNumberPhoneAccount(user, index);
                    break;
                }
                case 5: {
                    setGenderAccount(user, index);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("KHÔNG HỢP LỆ MỜI BẠN NHẬP LẠI");
                    break;
                }
            }
            productMenuWithUser.writeFile();
        } while (choice != 0);
    }

    private void setGenderAccount(User user, int index) {
        String sex = inputSex();
        user.setSex(sex);
        System.out.println("CHỈNH SỬA THÀNH CÔNG");
        userManagement.displayUserWithIndex(index);
    }

    private void setNumberPhoneAccount(User user, int index) {
        String numberPhone = inputNumberPhone();
        user.setNumberPhone(numberPhone);
        System.out.println("CHỈNH SỬA THÀNH CÔNG");
        userManagement.displayUserWithIndex(index);
    }

    private void setEmailAccount(User user, int index) {
        String email = inputEmail();
        user.setEmail(email);
        System.out.println("CHỈNH SỬA THÀNH CÔNG");
        userManagement.displayUserWithIndex(index);
    }

    private void setFullNameAccount(User user, int index) {
        String fullName = inputFullName();
        user.setFullName(fullName);
        System.out.println("CHỈNH SỬA THÀNH CÔNG");
        userManagement.displayUserWithIndex(index);
    }

    private void setPasswordAccount(User user, int index) {
        String password = inputPassword();
        user.setPassword(password);
        System.out.println("CHỈNH SỬA THÀNH CÔNG");
        userManagement.displayUserWithIndex(index);
    }

    private void subMenu() {
        System.out.println("CHỈNH SỬA THÔNG TIN TÀI KHOẢN");
        System.out.println("1. Đổi mật khẩu");
        System.out.println("2. Sửa họ và tên");
        System.out.println("3. Sửa email");
        System.out.println("4. Sửa số điện thoại");
        System.out.println("5. Sửa giới tính");
        System.out.println("0. Quay lại");
    }

    public void loginAccount() {
        if (userManagement.readFile("user.txt") == null) {
            System.out.println("Không tồn tại tài khoản nào trong hệ thống");
        } else {
            try {
                boolean isLogin;
                int count = 5;
                do {
                    System.out.println("ĐĂNG NHẬP");
                    System.out.println("Tài khoản : ");
                    String userName = scanner.nextLine();
                    System.out.println("Mật khẩu : ");
                    String password = scanner.nextLine();
                    User userLogin = new User(userName, password);
                    isLogin = userManagement.isLogin(userLogin);
                    if (isLogin) {
                        int index = userManagement.findIndex(userName);
                        User user = userManagement.getUsers().get(index);
                        if (user.getStatus().equals(BLOCK)) {
                            System.out.println("TÀI KHOẢN NÀY ĐANG BỊ KHÓA. KHÔNG THỂ TIẾN HÀNH ĐĂNG NHẬP");
                            return;
                        }
                        if (user.getRole().equals(CUSTOMER)) {
                            mainCustomer(user);
                        } else {
                            manageAccountAndProduct(user);
                        }
                    } else {
                        System.out.println("TÀI KHOẢN HOẶC MẬT KHẨU KHÔNG ĐÚNG VUI LÒNG THỬ LẠI!");
                        count--;
                        if (count > 0) {
                            System.out.println("Bạn còn " + count + " lần đăng nhập");
                        } else {
                            System.out.println("Bạn đã vượt quá số lần đăng nhập cho phép");
                        }
                    }
                } while (!isLogin && count > 0);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("CHƯA CÓ TÀI KHOẢN NÀO TRONG HỆ THỐNG");
            }
        }
    }

    private void mainCustomer(User user) {
        int choice;
        do {
            System.out.println("XIN CHÀO : " + user.getFullName());
            menuCustomer();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    productMenuWithUser.runProductMenuWithUser(user);
                    break;
                }
                case 2: {
                    changeAccountInfo(user);
                    break;
                }
                case 3: {
                    showUserInfo(user);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ BẠN CHỌN KHÔNG CÓ TRONG MENU. MỜI NHẬP LẠI");
                    break;
                }
            }
        } while (choice != 0);
    }

    private void showUserInfo(User user) {
        int index = userManagement.findIndex(user.getUseName());
        userManagement.displayUserWithIndex(index);
    }

    public void manageAccountAndProduct(User user) {
        int choice;
        do {
            menuAdmin();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    productMenuWithManage.runProductMenuWithManage();
                    break;
                }
                case 2: {
                    manageAllAccount(user);
                    break;
                }
                case 3: {
                    changeAccountInfo(user);
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    System.out.println("SỐ BẠN CHỌN KHÔNG HỢP LỆ. HÃY CHỌN LẠI");
                    break;
                }
            }
        } while (choice != 0);
    }

    private void manageAllAccount(User user) {
        int choice;
        do {
            menuManageUser();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    userManagement.displayAll();
                    break;
                }
                case 2: {
                    createStaffAccount();
                    break;
                }
                case 3: {
                    setStatusCustomer();
                    break;
                }
                case 4: {
                    findAccountUser();
                    break;
                }
                case 0: {
                    manageAccountAndProduct(user);
                    break;
                }
                default: {
                    System.out.println("SỐ NHẬP VÀO KHÔNG HỢP LỆ");
                    break;
                }
            }
        } while (choice != 0);
    }

    private void findAccountUser() {
        System.out.println("Nhập tài khoản muốn tìm kiếm : ");
        String userName = scanner.nextLine();
        int index = userManagement.findIndex(userName);
        if (index == -1) {
            System.out.println("Không tìm thấy tài khoản này trong hệ thống");
        }
        userManagement.displayUserWithIndex(index);
    }

    private void menuManageUser() {
        System.out.println("QUẢN LÝ HỘI VIÊN");
        System.out.println("1. Hiển thị danh sách hội viên");
        System.out.println("2. Tạo tài khoản cho nhân viên");
        System.out.println("3. Mở/ Khóa tài khoản");
        System.out.println("4. Kiểm tra tài khoản một hội viên");
        System.out.println("0. Quay lại");
    }

    private void setStatusCustomer() {
        System.out.println("Nhập tài khoản muốn chỉnh sửa : ");
        String userName = scanner.nextLine();
        int index = userManagement.findIndex(userName);
        if (index == -1) {
            System.out.println("Không tìm thấy tài khoản này trong hệ thống");
        } else {
            User user = userManagement.getUsers().get(index);
            int choice;
            do {
                menuBlockActiveAccount();
                choice = getChoice();
                switch (choice) {
                    case 1: {
                        blockAccount(user);
                        break;
                    }
                    case 2: {
                        activeAccount(user);
                        break;
                    }
                    default: {
                        System.out.println("SỐ BẠN CHỌN KHÔNG CÓ TRONG MENU. MỜI CHỌN LẠI");
                        choice = -1;
                        break;
                    }
                }
            } while (choice == -1);
            productMenuWithUser.writeFile();
        }
    }

    private void blockAccount(User user) {
        if (user.getStatus().equals(BLOCK)) {
            System.out.println("TÀI KHOẢN NÀY ĐÃ BỊ KHÓA TỪ TRƯỚC ĐÓ");
        } else {
            int subChoice;
            do {
                menuBlockAccount();
                subChoice = getChoice();
                switch (subChoice) {
                    case 1: {
                        user.setStatus(BLOCK);
                        break;
                    }
                    case 2: {
                        break;
                    }
                    default: {
                        System.out.println("SỐ CHỌN KHÔNG HỢP LỆ MỜI NHẬP LẠI");
                        subChoice = -1;
                        break;
                    }
                }
            } while (subChoice == -1);
        }
    }

    private void activeAccount(User user) {
        if (user.getStatus().equals(ACTIVE)) {
            System.out.println("TÀI KHOẢN NÀY VẪN ĐANG HOẠT ĐỘNG TỪ TRƯỚC ĐÓ");
        } else {
            int subChoice;
            do {
                menuActiveAccount();
                subChoice = getChoice();
                switch (subChoice) {
                    case 1: {
                        user.setStatus(ACTIVE);
                        break;
                    }
                    case 2: {
                        break;
                    }
                    default: {
                        System.out.println("SỐ CHỌN KHÔNG HỢP LỆ MỜI NHẬP LẠI");
                        subChoice = -1;
                        break;
                    }
                }
            } while (subChoice == -1);
        }
    }

    private void createStaffAccount() {
        User user = createUser();
        user.setRole(ADMIN);
        userManagement.addNew(user);
        productMenuWithUser.writeFile();
    }

    private void registerAccount() {
        System.out.println("ĐĂNG KÝ TÀI KHOẢN MỚI");
        User user = createUser();
        user.setRole(CUSTOMER);
        user.setStatus(ACTIVE);
        userManagement.addNew(user);
        productMenuWithUser.writeFile();
        System.out.println("ĐĂNG KÝ THÀNH CÔNG");
    }

    private void readFile() {
        userManagement.readFile(USER_PATH);
    }

    public User createUser() {
        String userName = inputUserName();
        String rePassword = inputPassword();
        String fullName = inputFullName();
        String email = inputEmail();
        String address = inputAddress();
        String numberPhone = inputNumberPhone();
        String sex = inputSex();
        return new User(userName, rePassword, fullName, email, address, numberPhone, sex);
    }

    private String inputAddress() {
        String address = "";
        do {
            System.out.println("Địa chỉ");
            address = scanner.nextLine();
            if (address.equals("")) {
                System.out.println("BẠN CHƯA NHẬP TÊN");
            } else if (address.length() < 2) {
                System.out.println("TÊN KHÔNG HỢP LỆ");
            }
        } while (address.length() < 2);
        return address;
    }

    private String inputSex() {
        String sex = "";
        int choice;
        do {
            menuSex();
            choice = getChoice();
            switch (choice) {
                case 1: {
                    sex = "Nam";
                    break;
                }
                case 2: {
                    sex = "Nữ";
                    break;
                }
                case 3: {
                    sex = "Khác";
                    break;
                }
                default: {
                    System.out.println("KHÔNG HỢP LỆ MỜI BẠN CHỌN LẠI");
                    break;
                }
            }
        } while (sex.equals(""));
        return sex;
    }

    private void menuSex() {
        System.out.println("CHỌN GIỚI TÍNH");
        System.out.println("1. Nam");
        System.out.println("2. Nữ");
        System.out.println("3. Khác");
    }

    private String inputNumberPhone() {
        String numberPhone;
        final String PHONE = "^[0]{1}+[235789]{1}+\\d{8}$";
        Pattern patternPhone = Pattern.compile(PHONE);
        Matcher matcherPhone;
        do {
            System.out.println("Số điện thoại : ");
            numberPhone = scanner.nextLine();
            matcherPhone = patternPhone.matcher(numberPhone);
            if (!matcherPhone.matches()) {
                System.out.println("SỐ ĐIỆN THOẠI KHÔNG HỢP LỆ\nSố điện thoại phải bắt đầu bằng số 0 và có 10 số");
            }
        } while (!matcherPhone.matches());
        return numberPhone;
    }

    private String inputEmail() {
        String email;
        final String EMAIL = "^[a-zA-Z]+[A-Za-z0-9]*@[a-zA-Z]+(\\.com)$";
        Pattern patternMail = Pattern.compile(EMAIL);
        Matcher matcherMail;
        do {
            System.out.println("Email : ");
            email = scanner.nextLine();
            matcherMail = patternMail.matcher(email);
            if (!matcherMail.matches()) {
                System.out.println("EMAIL KHÔNG HỢP LỆ MỜI NHẬP LẠI");
            }
        } while (!matcherMail.matches());
        return email;
    }

    private String inputFullName() {
        String fullName;
        final String NAME = "^([a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+)$";
        Pattern patternName = Pattern.compile(NAME);
        Matcher matcherName;
        do {
            System.out.println("Họ và tên : ");
            fullName = scanner.nextLine();
            matcherName = patternName.matcher(fullName);
            if (fullName.equals("")) {
                System.out.println("BẠN CHƯA NHẬP TÊN");
            } else if (fullName.length() < 2 || !matcherName.matches()) {
                System.out.println("TÊN KHÔNG HỢP LỆ! VUI LÒNG NHẬP LẠI");
            }
        } while (fullName.length() < 2 || !matcherName.matches());
        return fullName;
    }

    private String inputPassword() {
        String password;
        String rePassword;
        do {
            System.out.println("Mật khẩu : ");
            password = scanner.nextLine();
            if (password.length() < 6 || password.length() > 17) {
                System.out.println("Mật khẩu quá dài hoặc quá ngắn. Mật khẩu trong khoảng 6 -> 16 kí tự");
            }
        } while (password.length() < 6 || password.length() > 17);
        do {
            System.out.println("Nhập lại mật khẩu : ");
            rePassword = scanner.nextLine();
            if (!rePassword.equals(password)) {
                System.out.println("XÁC NHẬN MẬT KHẨU KHÔNG ĐÚNG MỜI NHẬP LẠI");
            }
        } while (!rePassword.equals(password));
        return rePassword;
    }

    private String inputUserName() {
        int index;
        String userName;
        final String USER_NAME = "^\\w{6,16}$";
        Pattern pattern = Pattern.compile(USER_NAME);
        Matcher matcher;
        do {
            System.out.println("Tên đăng nhập : ");
            userName = scanner.nextLine();
            matcher = pattern.matcher(userName);
            index = userManagement.findIndex(userName);
            if (!matcher.matches()) {
                System.out.println("TÀI KHOẢN KHÔNG HỢP LỆ!\nTên đăng nhập phải có từ 6 - 16 ký tự");
            }
            if (index != -1) {
                System.out.println("TÀI KHOẢN ĐÃ TỒN TẠI MỜI NHẬP LẠI");
            }
        } while (!matcher.matches() || index != -1);
        return userName;
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

    private void menu() {
        System.out.println("----------HỆ THỐNG QUẢN TRỊ NGƯỜI DÙNG----------");
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng ký");
        System.out.println("0. Thoát");
    }

    private void menuBlockActiveAccount() {
        System.out.println("MỞ/KHÓA TÀI KHOẢN");
        System.out.println("1. Khóa tài khoản");
        System.out.println("2. Mở tài khoản");
        System.out.println("0. Quay lại");
    }

    private void menuActiveAccount() {
        System.out.println("BẠN THỰC SỰ MUỐN MỞ KHÓA CHO TÀI KHOẢN NÀY");
        System.out.println("1. Mở khóa");
        System.out.println("2. Quay lại");
    }

    private void menuBlockAccount() {
        System.out.println("BẠN THỰC SỰ MUỐN KHÓA TÀI KHOẢN NÀY");
        System.out.println("1. Khóa");
        System.out.println("2. Quay lại");
    }

    private void menuAdmin() {
        System.out.println("QUẢN LÝ CỬA HÀNG");
        System.out.println("1. Quản lý sản phẩm");
        System.out.println("2. Quản lý hội viên");
        System.out.println("3. Thay đổi thông tin cá nhân");
        System.out.println("0. Đăng xuất");
    }

    private void menuCustomer() {
        System.out.println("MỜI BẠN LỰA CHỌN");
        System.out.println("1. Vào gian hàng");
        System.out.println("2. Thay đổi thông tin cá nhân");
        System.out.println("3. Hiển thị thông tin cá nhân");
        System.out.println("0. Đăng xuất");
    }
}