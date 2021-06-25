package org;

import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

public class Main1 {
    public static void main(String[] args) {
        for (int i = 1 ; i < 100 ; i++){
            int y = (2 + 2 * i) % 5;
            if (i % 5 == y ){
                System.out.println(i);
            }
        }
    }
}
