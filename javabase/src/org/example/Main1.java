package org.example;

public class Main1 {
    public static void main(String[] args) {
        B b = new B();
        String aa = b.trans("aa");
        System.out.println(aa);
    }
}

class A<T>{
    public A(){
    }

    public A(String a) {
        System.out.println(a);
    }

    public T trans(T b){
        System.out.println(b);
        return b;
    }
}

class B extends A<String>{
    public B() {

    }

    @Override
    public String trans(String b) {
        return b + "!!";
    }
}
