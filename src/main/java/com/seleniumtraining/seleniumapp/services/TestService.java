package com.seleniumtraining.seleniumapp.services;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seleniumtraining.seleniumapp.chromedriver.ChromeDriverSetup;

@Service
public class TestService {


    @Autowired
    private ChromeDriverSetup driverManager;

    public void runTest() {
        WebDriver driver = driverManager.startDriver();
        driver.get("https://www.google.com");
        System.out.println("page title is: " + driver.getTitle());
        driverManager.stopDriver();
    }
}
