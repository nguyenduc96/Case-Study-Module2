package controller;

public interface IGeneralManagement<T> {

    void addNew(T t);

    void delete(int index);

    void displayAll();

    void update(int index, T t);

    int findIndex(String string);
}
