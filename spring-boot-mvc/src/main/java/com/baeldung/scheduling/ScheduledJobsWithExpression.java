package com.baeldung.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduledJobsWithExpression
{
   private final static Logger LOG =
      LoggerFactory.getLogger(ScheduledJobsWithExpression.class);

   /**
    * A scheduled job controlled via application property. The job always
    * executes, but the logic inside is protected by a configurable boolean
    * flag.
    */
   @Scheduled(cron = "${jobs.cronSchedule:-}")
   public void cleanTempDirectory() {
         LOG.info("Cleaning temp directory via placeholder");
   }
}
