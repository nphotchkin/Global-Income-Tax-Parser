package com.generic.tax.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryRate {

    private String lowerAmountBracket;

    private String upperAmountBracket;

    private String taxRate;

    private String currencySymbol;


}
