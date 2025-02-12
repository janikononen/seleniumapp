package com.seleniumtraining.seleniumapp.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.seleniumtraining.seleniumapp.domain.Yritys;

@Service
public class DataScraper {

    public List<String> searchCompanyUrls(WebDriver driver, String url) {
        driver.get(url);

        List<WebElement> yritykset = driver.findElements(By.cssSelector("div#main_content_anchor ul li a"));
        List<String> yritysUrls = new ArrayList<>();
        for (WebElement elementti : yritykset) {
            String yritysUrl = elementti.getAttribute("href");
            System.out.println(yritysUrl);
            yritysUrls.add(yritysUrl);
        }
        return yritysUrls;
    }

    public void searchCompanyData(WebDriver driver, List<String> yritykset) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        String status = "";
        Yritys yritys = new Yritys();

        for (String yritysSivu : yritykset) {
            String datatype = "";
            String data = "";
            System.out.println(yritysSivu);
            driver.get(yritysSivu);
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.cssSelector(
                            "h2#basic-information + ul li span span")));

            // haetaan yrityksen status
            try {
                status = driver.findElement(By.cssSelector(".sc-5564dx-3.hfOAuF")).getText();
            } catch (Exception e) {
                status = driver.findElement(By.cssSelector(".sc-5564dx-3.eZHZlC")).getText();
            }
            System.out.println(status);

            // haetaan yrityksen tiedot jos yrityksen status on aktiivinen
            if (status.equals("AKTIIVINEN")) {
                List<WebElement> companyData = driver
                        .findElements(By.cssSelector("h2#basic-information + ul li"));// span:not(span
                                                                                      // span):not(span

                System.out.println(companyData.size());
                for (WebElement dataLine : companyData) {
                    wait.until(ExpectedConditions.visibilityOf(dataLine));
                    String[] typeAndData = dataLine.getText()
                            .replace("(YTJ)", "").replace("Katso sijainti kartalta", "")
                            .replace("(Kaupparekisteri)", "").replace("Lue lisää", "").trim().split(":");

                    datatype = typeAndData[0];
                    data = typeAndData[1];

                    System.out.println(datatype + " " + data);

                    if (datatype.equals("Y-tunnus")) {
                        System.out.println("Y-tunnus: " + data);
                        yritys.setyTunnus(data);
                    } else if (datatype.equals("Yrityksen nimi")) {
                        System.out.println("Yrityksen nimi: " + data);
                        yritys.setYritysNimi(data);
                    } else if (datatype.equals("Toimitusjohtaja")) {
                        System.out.println("Toimitusjohtaja: " + data);
                        yritys.setToimitusjohtaja(data);
                    } else if (datatype.equals("Postiosoite")) {
                        System.out.println("Postiosoite: " + data);
                        String[] osoitedata = data.split(",");
                        yritys.setPostiosoite(osoitedata[0]);
                        String[] postinumeroJaPostitoimipaikka = osoitedata[1].trim().split(" ");
                        yritys.setPostinumero(postinumeroJaPostitoimipaikka[0]);
                        yritys.setPostitoimipaikka(postinumeroJaPostitoimipaikka[1]);
                    } else if (datatype.equals("Puhelinnumero")) {
                        System.out.println("Puhelinnumero: " + data);
                        yritys.setPuhelinnumero(data);
                    } else if (datatype.equals("Sähköposti")) {
                        System.out.println("Sähköposti: " + data);
                        yritys.setSahkoposti(data);
                    }
                }

            }
        }
    }
}