package org.example;

class StaticBlock{
    public static  StaticBlock MPP = new StaticBlock();

    public void show(){
        System.out.println("内部show方法");
    }
}

public class Test {
    public static void main(String[] args)
    {

    }
}


