package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 参与序列化和反序列化要实现Serializable接口,Serializable接口是一个标志接口,没有任何抽象方法,起到一个标识作用
public class Student implements Serializable {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        Integer count = 0;
    }
}





