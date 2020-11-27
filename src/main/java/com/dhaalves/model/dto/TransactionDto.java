package com.dhaalves.model.dto;

import com.dhaalves.model.CurrencySymbol;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Serializable {

  @NotEmpty
  private String userId;
  @NotNull
  private CurrencySymbol sourceCurrency;
  @NotNull
  private BigDecimal value;
  @NotNull
  private CurrencySymbol targetCurrency;
}
