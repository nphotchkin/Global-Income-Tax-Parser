package com.generic.tax.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CountryDomesticPersonTaxBracket {

    private String isoCountryCode;

    private String isoCurrency;

    List<CountryRate> rates;

}
