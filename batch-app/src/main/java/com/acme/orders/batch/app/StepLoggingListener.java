package com.acme.orders.batch.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StepLoggingListener extends StepExecutionListenerSupport {
  @Override public void beforeStep(StepExecution stepExecution) {
    log.info(">>> Step {} starting, jobParams={}", stepExecution.getStepName(), stepExecution.getJobParameters());
  }
  @Override public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("<<< Step {} end read={} write={} skip={}", stepExecution.getStepName(),
        stepExecution.getReadCount(), stepExecution.getWriteCount(), stepExecution.getSkipCount());
    return stepExecution.getExitStatus();
  }
}
