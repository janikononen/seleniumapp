package com.seleniumtraining.seleniumapp.chromedriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import io.github.bonigarcia.wdm.WebDriverManager;

@Component
public class ChromeDriverSetup {
    private WebDriver driver;

    public WebDriver startDriver(){
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        return driver;
    }

    public void stopDriver(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
