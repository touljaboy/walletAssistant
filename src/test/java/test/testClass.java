package test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

abstract class configurationClass {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("beforeMethod");
    }

}

public class testClass extends configurationClass {

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

}
