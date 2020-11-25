package com.dhaalves.dto;

import com.dhaalves.model.Currency;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionDto implements Serializable {
  @NotNull private String userId;
  @NotNull private Currency sourceCurrency;
  @NotNull private BigDecimal value;
  @NotNull private Currency targetCurrency;
}
