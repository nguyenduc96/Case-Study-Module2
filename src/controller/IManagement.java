package controller;

import java.util.List;

public interface IManagement<T> extends IGeneralManagement<T> {

    List<T> readFile(String path);

    void writeFile(String path);
}
