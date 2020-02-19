package com.generic.tax.service;

import com.generic.tax.model.CountryDomesticPersonTaxBracket;
import com.generic.tax.model.CountryRate;
import com.generic.tax.util.SeleniumUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Implicit wait
 */
public class IRSCalculatorsScraper {

    private WebDriver driver;

    private Long totalCountries = 0L; // 0 indexed?

    public IRSCalculatorsScraper(WebDriver driver) {
        this.driver = driver;
        setTotalCountries();
    }

    public void selectCountry(int index) {
        Select locationSelector =  new Select(driver.findElement(By.id("locationSelector1")));
        locationSelector.selectByIndex(index);
        waitForDomesticPersonResults(driver);
    }

    public CountryDomesticPersonTaxBracket getTaxBracketsForSelectedCountry() {
        List<CountryRate> countryRates = new ArrayList<>();

        JavascriptExecutor jse = (JavascriptExecutor)driver;


        boolean hasResultsTable = true;
        while (hasResultsTable) {
            for (int i = 0; i < 20; i ++) {

                try {

                    WebElement taxBracketRowDiv = driver.findElement(By.id("1resultsTableDiv" + i));
                    countryRates.add(
                            CountryRate.builder()
                                    .lowerAmountBracket(taxBracketRowDiv.findElement(By.className("lowerBracket")).getText())
                                    .upperAmountBracket(taxBracketRowDiv.findElement(By.className("higherBracket")).getText())
                                    .taxRate(taxBracketRowDiv.findElement(By.className("taxRate")).getText())
                                    .currencySymbol(taxBracketRowDiv.findElement(By.className("countryCurrencySymbol1")).getText())
                                    .build()
                    );


                } catch (NoSuchElementException e) {


                    hasResultsTable = false;
                    break;
                }
            }
        }



        return CountryDomesticPersonTaxBracket.builder()
                .isoCountryCode((String) jse.executeScript("return document.getElementById(\"locationSelector1\").value; "))
                .isoCurrency( (String) jse.executeScript("return document.getElementById(\"displayCurrency1\").value;"))
                .rates(countryRates)
                .build();
    }


    public Long getTotalCountries() {
        return this.totalCountries;
    }

    private void waitForDomesticPersonResults(WebDriver driver) {
        SeleniumUtil.waitUntilElementExists(driver, By.id("1resultsTableDiv0"));
    }

    private void setTotalCountries() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        this.totalCountries = (Long) jse.executeScript("return document.getElementById(\"locationSelector1\").options.length") - 1;
    }



    private static double toDoubleValue(String string) {
        String numericString = string.replaceAll("[^\\d.]", "");

        if (!numericString.equals("")) {
            return Double.valueOf(numericString);
        } else {
            return -1;
        }
    }



}
