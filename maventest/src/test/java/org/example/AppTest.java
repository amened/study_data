package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testAdd(){
        System.out.println("[INFO] 测试App类中的方法");
        App app = new App();
        int set = app.add(30,60);
        Assert.assertEquals(90 , set);
    }
}
