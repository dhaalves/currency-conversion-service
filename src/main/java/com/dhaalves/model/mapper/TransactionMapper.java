package com.dhaalves.model.mapper;

import com.dhaalves.model.dto.TransactionDto;
import com.dhaalves.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

  TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

  TransactionDto transactionToTransactionDto(Transaction transaction);

  Transaction transactionDtoToTransaction(TransactionDto transactionDto);
}
