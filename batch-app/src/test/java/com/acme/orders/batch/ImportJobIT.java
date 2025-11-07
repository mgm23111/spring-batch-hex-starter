package com.acme.orders.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ImportJobIT {

  @Autowired
  private JobLauncherTestUtils utils;

  @Test
  void runJob() throws Exception {
    var params = new JobParametersBuilder()
        .addString("file", "src/test/resources/orders.csv")
        .addLong("ts", System.currentTimeMillis())
        .toJobParameters();

    JobExecution exec = utils.launchJob(params);
    assert exec.getStatus() == BatchStatus.COMPLETED;
  }
}
