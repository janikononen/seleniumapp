package com.seleniumtraining.seleniumapp.services;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seleniumtraining.seleniumapp.chromedriver.ChromeDriverSetup;

@Service
public class TestService {

    @Autowired
    private ChromeDriverSetup driverManager;

    public void runTest() {
        WebDriver driver = driverManager.startDriver();
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1)); //halutessaan voi muuttaa
        driver.get("https://duunitori.fi/tyopaikat?haku=it-ala");
        System.out.println("page title is: " + driver.getTitle());
        driverManager.stopDriver();
    }


}
