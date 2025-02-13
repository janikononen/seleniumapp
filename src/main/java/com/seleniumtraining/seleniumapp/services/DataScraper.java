package com.seleniumtraining.seleniumapp.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seleniumtraining.seleniumapp.domain.Yritys;
import com.seleniumtraining.seleniumapp.domain.YritysRepository;

@Service
public class DataScraper {

    @Autowired
    private YritysRepository yritysRepository;

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

        for (String yritysSivu : yritykset) {
            Yritys yritys = new Yritys();
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
                yritys.setWwwOsoite(status);
                yritys.setYritysNimi(
                        driver.findElement(By.cssSelector(".sc-16wyvoq-1.sc-5564dx-1.bcVLjX.dmajRS")).getText());

                List<WebElement> companyData = driver
                        .findElements(By.cssSelector("h2#basic-information + ul li"));
                System.out.println(companyData.size());

                for (WebElement dataLine : companyData) {
                    wait.until(ExpectedConditions.visibilityOf(dataLine));
                    String[] typeAndData = dataLine.getText()
                            // poistetaan turhat tekstit
                            .replace("(YTJ)", "").replace("Katso sijainti kartalta", "")
                            .replace("(Kaupparekisteri)", "").replace("Lue lisää", "").replace("\n", "").trim()
                            .split(":");
                    try {
                        datatype = typeAndData[0];
                        data = typeAndData[1];

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
                        } else if (datatype.equals("Puhelin")) {
                            System.out.println("Puhelinnumero: " + data);
                            yritys.setPuhelinnumero(data);
                        } else if (datatype.equals("Sähköposti")) {
                            System.out.println("Sähköposti: " + data);
                            yritys.setSahkoposti(data);
                        } else if (datatype.equals("Toimialakuvaus")) {
                            System.out.println("Toimialakuvaus: " + data);
                            yritys.setToimialakuvaus(data);
                        }
                    } catch (Exception e) {
                        System.out.println("Tietoja ei löytynyt tai tapahtui virhe: " + e);
                    }

                }
                yritysRepository.save(yritys);
            }
        }
    }
}