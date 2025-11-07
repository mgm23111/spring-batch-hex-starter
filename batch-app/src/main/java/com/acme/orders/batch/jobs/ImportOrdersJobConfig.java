package com.acme.orders.batch.jobs;

import com.acme.orders.batch.domain.model.Order;
import com.acme.orders.batch.infra.reader.OrderCsv;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.StepBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportOrdersJobConfig {

  @Bean
  public Job importOrdersJob(JobRepository repo, Step importStep) {
    return new JobBuilder("importOrdersJob", repo)
        .incrementer(new RunIdIncrementer())
        .start(importStep)
        .build();
  }

  @Bean
  public Step importStep(JobRepository repo,
                         PlatformTransactionManager tx,
                         ItemReader<OrderCsv> reader,
                         ItemProcessor<OrderCsv, Order> processor,
                         ItemWriter<Order> writer) {

    return new StepBuilder("importStep", repo)
        .<OrderCsv, Order>chunk(500, tx)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
          .skip(IllegalArgumentException.class).skipLimit(100)
        .build();
  }
}
