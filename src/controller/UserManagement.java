package controller;

import model.User;

import java.io.*;
import java.util.*;

import static view.UserMenu.*;

public class UserManagement implements IManagement<User> {
    List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void addNew(User user) {
        users.add(user);
    }

    @Override
    public void delete(int index) {
        users.remove(index);
    }

    @Override
    public void displayAll() {
        for (int i = 0; i < users.size(); i++) {
            displayUserWithIndex(i);
        }
    }

    public String displayInfoUser(User user) {
        return "THÔNG TIN TÀI KHOẢN " + "\nTên đăng nhập : " + user.getUseName() +
                "\nHọ và tên : " + user.getFullName() + "\nEmail : " + user.getEmail() +
                "\nĐịa chỉ : " + user.getAddress() + "\nSố điện thoại : " + user.getNumberPhone() +
                "\nGiới tính : " + user.getSex() + "\nQuyền hạn : " + user.getRole() +
                "\nTrạng thái tài khoản : " + user.getStatus();
    }

    public void displayUserWithIndex(int index) {
        User user = users.get(index);
        if (user.getRole().equals(ADMIN)) {
            System.out.println(displayInfoUser(user));
        }
        boolean checkCart = user.getCarts() == null;
        boolean checkBill = user.getBills() == null;
        boolean checkCustomer = user.getRole().equals(CUSTOMER);
        if (checkCart && checkCustomer) {
            if (checkBill) {
                System.out.println(displayInfoUser(user) + "\nCHƯA CÓ ĐƠN HÀNG NÀO");
            } else {
                System.out.println(displayInfoUser(user) + "\n" + user.getBills());
            }
        } else if (!checkCart && checkCustomer) {
            if (checkBill) {
                System.out.println(displayInfoUser(user) + "\n" + user.getCarts());
            } else {
                System.out.println(displayInfoUser(user) + "\n" + user.getCarts() + "\n" + user.getBills());
            }
        }
    }

    @Override
    public void update(int index, User user) {
        users.set(index, user);
    }

    @Override
    public int findIndex(String username) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUseName())) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public List<User> readFile(String path) {
        try {
            InputStream inputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            users = (List<User>) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (EOFException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void writeFile(String path) {
        try {
            OutputStream outputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(users);
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLogin(User userLogin) {
        for (User user : users) {
            if (user.getUseName().equals(userLogin.getUseName())
                    && user.getPassword().equals(userLogin.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
