package controller;

import model.Bill;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BillManagement implements IManagement<Bill> {
    List<Bill> bills = new ArrayList<>();

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public void addNew(Bill bill) {
        bills.add(bill);
    }

    @Override
    public void delete(int index) {
        bills.remove(index);
    }

    @Override
    public void displayAll() {
        for (Bill bill : bills) {
            System.out.println(bill);
        }
    }

    @Override
    public void update(int index, Bill bill) {
        bills.set(index, bill);
    }

    @Override
    public int findIndex(String id) {
        int index = -1;
        for (int i = 0; i < bills.size(); i++) {
            if (id.equals(bills.get(i).getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public List<Bill> readFile(String path) {
        try {
            InputStream inputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            bills = (List<Bill>) objectInputStream.readObject();
            inputStream.close();
            objectInputStream.close();
        } catch (EOFException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public void writeFile(String path) {
        try {
            OutputStream outputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(bills);
            outputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long totalRevenue() {
        long total = 0;
        for (Bill bill : bills) {
            total += bill.getCart().realMoney();
        }
        return total;
    }
}
