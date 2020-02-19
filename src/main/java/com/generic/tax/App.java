package com.generic.tax;

import com.generic.tax.model.CountryDomesticPersonTaxBracket;
import com.generic.tax.model.CountryRate;
import com.generic.tax.service.IRSCalculatorsScraper;
import com.generic.tax.util.Rng;
import com.generic.tax.util.SeleniumUtil;
import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.irscalculators.com/international-tax-calculator");


        IRSCalculatorsScraper irsCalculatorsScraper = new IRSCalculatorsScraper(driver);


        List<CountryDomesticPersonTaxBracket> countryDomesticPersonTaxBrackets = new ArrayList<>();


        Rng rng = new Rng();


        for (int i = 0; i < irsCalculatorsScraper.getTotalCountries();  i++) {

            Thread.sleep((long)(4 * 1000));

            int wait = rng.getRandomNumber(10, 20);
            System.out.println("Wait: " + wait);
            System.out.println(new Date().toString());
            System.out.println("Country Number: " + i);

            irsCalculatorsScraper.selectCountry(i);

            Thread.sleep((long)(wait * 1000));

            countryDomesticPersonTaxBrackets.add(irsCalculatorsScraper.getTaxBracketsForSelectedCountry());
        }


        Gson gson = new Gson();

        System.out.println(gson.toJson(countryDomesticPersonTaxBrackets));




     // CountryDomesticPersonTaxBracket tb =  IRSCalculatorsScraper.getTaxBracketsForSelectedCountry(driver);




     //   driver.quit();

     //   System.out.println(domesticPerson);

//        // accept privacy statement
//        WebDriverWait waitForPrivacyStatement = new WebDriverWait(driver, 5);
//        waitForPrivacyStatement.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("qc-cmp-showing"))));
//        WebElement acceptPrivacyStatement = driver.findElement(By.className("qc-cmp-button"));
//        acceptPrivacyStatement.click();
    }




}
