package com.dhaalves.model.entity;

import com.dhaalves.model.CurrencySymbol;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "transactions")
public class Transaction  extends PanacheEntityBase implements Serializable {

  @Id
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "UUID")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "user_id")
  @NotEmpty
  private String userId;

  @NotNull
  @Column(name = "source_currency")
  private CurrencySymbol sourceCurrency;

  @NotNull
  private BigDecimal value;

  @NotNull
  @Column(name = "target_currency")
  private CurrencySymbol targetCurrency;

  @NotNull
  @Column(name = "exchange_rate")
  private BigDecimal exchangeRate;

  @CreationTimestamp
  private LocalDateTime dateTime;

  /**
   * This will be used on JSON serialization, and will be returned when you query for
   * transactions
   *
   * @return
   */
  public BigDecimal getConvertedValue() {
    return value.multiply(exchangeRate);
  }

  /**
   * Find transaction by userId (ref: Active Record Pattern)
   *
   * @param userId
   * @return
   */
  public static List<Transaction> findByUserId(String userId) {
    return list("user_id", userId);
  }
}
