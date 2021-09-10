package controller;

import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static view.UserMenu.scanner;

public class ProductManagement implements IManagement<Product> {
    List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void displayWithTypeProduct(String type) {
        boolean checkType = checkTypeInListProduct(type);
        if (checkType) {
            for (Product product : products) {
                System.out.println(product);
            }
        } else {
            System.out.println("HIỆN TẠI CHƯA CÓ SẢN PHẨM NÀO CỦA LOẠI HÀNG NÀY");
        }
    }

    public boolean checkTypeInListProduct(String type) {
        for (Product product : products) {
            if (type.contains(product.getType())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCompanyInListProduct(String company) {
        for (Product product : products) {
            if (company.contains(product.getCompany())) {
                return true;
            }
        }
        return false;
    }

    public void displayWithCompanyProduct(String company) {
        boolean checkCompany = checkCompanyInListProduct(company);
        if (checkCompany) {
            for (Product product : products) {
                System.out.println(product);
            }
        } else {
            System.out.println("HIỆN TẠI CHƯA CÓ SẢN PHẨM NÀO CỦA HÃNG NÀY");
        }
    }

    @Override
    public void addNew(Product product) {
        products.add(product);
    }

    @Override
    public void delete(int index) {
        products.remove(products);
    }

    @Override
    public void displayAll() {
        int count = 0;
        for (Product p : products) {
            System.out.println(p);
            count ++;
            if (count == 5) {
                count =0;
                System.out.println("Ấn ENTER để xem tiếp");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void update(int index, Product product) {
        products.set(index, product);
    }

    @Override
    public int findIndex(String id) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public List<Product> readFile(String path) {
        try {
            InputStream inputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            products = (List<Product>) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (EOFException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void writeFile(String path) {
        try {
            OutputStream outputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(products);
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortBigToSmallPrice() {
        Product product;
        int position;
        for (int i = 1; i < products.size(); i++) {
            product = products.get(i);
            position = i;
            while (position > 0 && (product.getPrice() > products.get(position - 1).getPrice())) {
                products.set(position, products.get(position - 1));
                position--;
            }
            products.set(position, product);
        }
    }

    public void sortSmallToBigPrice() {
        Product product;
        int position;
        for (int i = 1; i < products.size(); i++) {
            product = products.get(i);
            position = i;
            while (position > 0 && (product.getPrice() < products.get(position - 1).getPrice())) {
                products.set(position, products.get(position - 1));
                position--;
            }
            products.set(position, product);
        }
    }
}
