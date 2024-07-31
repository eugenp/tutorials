create table book (
    id bigint(20) AUTO_INCREMENT primary key,
    name varchar(255) not null,
    uuid varchar(40)
);


DELIMITER ;;
DROP PROCEDURE IF EXISTS populate;
create procedure populate()
BEGIN
    SET @name1='Josh purchase';
    SET @name2='Henry purchase';
    SET @name3='Betty purchase';
    SET @name4='Kate purchase';
    SET @name5='Mari purchase';
    SET @name='';
    SET @counter=0;

    START TRANSACTION;

    while @counter < 1000000 do
        SET @name = case
                    when MOD(@counter, 5) = 0 THEN @name5
                    when MOD(@counter, 3) = 0 THEN @name3
                    when MOD(@counter, 4) = 0 THEN @name4
                    when MOD(@counter, 2) = 0 THEN @name2
                    else @name1
            end;

        insert into book(name, uuid) values(@name, uuid());
        SET @counter=@counter+1;
    end while;

    COMMIT;

END;;

DELIMITER ;

CALL populate();


