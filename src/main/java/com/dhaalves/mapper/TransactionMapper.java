package com.dhaalves.mapper;

import com.dhaalves.dto.TransactionDto;
import com.dhaalves.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
  TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

  TransactionDto transactionToTransactionDto(Transaction transaction);

  Transaction transactionDtoToTransaction(TransactionDto transactionDto);
}
