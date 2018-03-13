//<start id="scheduled_fixedRate"/> 
@Scheduled(fixedRate=86400000)
public void archiveOldSpittles() {
  // ...
}
//<end id="scheduled_fixedRate"/> 

//<start id="scheduled_fixedDelay"/> 
@Scheduled(fixedDelay=86400000)
public void archiveOldSpittles() {
  // ...
}
//<end id="scheduled_fixedDelay"/> 

//<start id="scheduled_cron"/> 
@Scheduled(cron="0 0 0 * * SAT")
public void archiveOldSpittles() {
  // ...
}
//<end id="scheduled_cron"/> 

//<start id="scheduled_cron2"/> 
@Scheduled(cron="0 0 0 * * SAT")
public void archiveOldSpittles() {
  // ...
}
//<end id="scheduled_cron2"/> 

//<start id="asyncMethod"/> 
@Async
public void addSpittle(Spittle spittle) {
    ...
}
//<end id="asyncMethod"/> 

//<start id="asyncMethod2"/> 
@Async
public Future<Long> performSomeReallyHairyMath(long input) {
    // ...
    
    return new AsyncResult<Long>(result);
}
//<end id="asyncMethod2"/> 
