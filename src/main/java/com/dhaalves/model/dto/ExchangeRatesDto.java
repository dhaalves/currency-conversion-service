package com.dhaalves.model.dto;

import com.dhaalves.model.CurrencySymbol;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class ExchangeRatesDto implements Serializable {

  private Map<String, Double> rates;
  private String base;
  private String date;

  public BigDecimal getRate(CurrencySymbol currencySymbol) {
    return BigDecimal.valueOf(rates.get(currencySymbol.name()));
  }

}
