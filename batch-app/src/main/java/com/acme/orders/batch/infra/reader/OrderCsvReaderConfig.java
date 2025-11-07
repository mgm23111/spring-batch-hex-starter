package com.acme.orders.batch.infra.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.batch.core.configuration.annotation.StepScope;

@Configuration
public class OrderCsvReaderConfig {

  @Bean
  @StepScope
  public FlatFileItemReader<OrderCsv> orderCsvReader(@Value("#{jobParameters['file']}") String file) {
    return new FlatFileItemReaderBuilder<OrderCsv>()
        .name("orderCsvReader")
        .resource(new FileSystemResource(file))
        .linesToSkip(1)
        .delimited()
        .names("orderId","customerId","amount","currency","createdAt")
        .targetType(OrderCsv.class)
        .build();
  }
}
