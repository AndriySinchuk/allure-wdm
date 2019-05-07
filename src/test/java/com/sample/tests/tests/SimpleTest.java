package com.sample.tests.tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class SimpleTest extends AbstractWebTest{


    @AfterTest
    public void finish() {
        System.out.println("--RUNNING TEST-- SimpleTest");
    }
    @Test (priority = 1, description = "qwerty")
    public void sampleTest(){
        openPage("https://www.playtika.com/");
    }




}
