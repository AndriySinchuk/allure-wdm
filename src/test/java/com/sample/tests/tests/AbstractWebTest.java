package com.sample.tests.tests;

import tools.TestConfig;


public class AbstractWebTest extends TestConfig {

    public void openPage(String page) {
        getDriver().get(page);
    }

}
