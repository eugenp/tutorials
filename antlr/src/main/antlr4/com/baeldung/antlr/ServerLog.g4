grammar ServerLog;

log : logEntry+ EOF ;
logEntry : datetime WS level WS message NEWLINE;
datetime : date WS dateTime;
message : (WORD | WS)+ ;
level : ('DEBUG' | 'INFO' | 'ERROR');
date : NUMBER PAUSE month PAUSE NUMBER;
dateTime : NUMBER COLON NUMBER COLON NUMBER;

month
   : 'Jan'
   | 'Feb'
   | 'Mar'
   | 'Apr'
   | 'May'
   | 'Jun'
   | 'Jul'
   | 'Aug'
   | 'Sep'
   | 'Oct'
   | 'Nov'
   | 'Dec'
   ;

fragment LOWERCASE  : [a-z] ;
fragment UPPERCASE  : [A-Z] ;
fragment DIGIT : [0-9];
WORD                : (LOWERCASE | UPPERCASE | '_')+ ;
WS          : (' ' | '\t') ;
NEWLINE             : ('\r'? '\n' | '\r')+ ;
PAUSE               : '-';
COLON               : ':';
NUMBER : DIGIT+;