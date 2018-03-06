# v0.1.5
USE todolist;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS task;

CREATE TABLE user (
  id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR (100) NOT NULL,
  name VARCHAR (60) NOT NULL,
  password VARCHAR (60) NOT NULL,
  mobile VARCHAR (30) NOT NULL DEFAULT "",
  location VARCHAR (30) NOT NULL DEFAULT "",
  avatar VARCHAR (150) NOT NULL DEFAULT "",
  wechatid VARCHAR (50) NOT NULL DEFAULT "",
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  # 0: user
  # 1: admin
  role INTEGER NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE task (
  id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  # FK USER
  belongs_to INTEGER NOT NULL DEFAULT 0,
  belongs_to_name VARCHAR(60) NOT NULL,
  belongs_to_email VARCHAR(100) NOT NULL,
  # status:
  # 0: open
  # 1: close
  status INTEGER NOT NULL DEFAULT 0,
  # category
  # 0: Your Daily To-Do List
  # 1: The Weekly/Monthly Project List (short-term 30 days)
  # 2: The Master Goal List (long-term, 90-180 days)
  category INTEGER NOT NULL DEFAULT 0,
  content VARCHAR (200) NOT NULL DEFAULT "",
  # 3: highest
  # 2: higher
  # 1: normal
  # 0: low
  priority INTEGER NOT NULL DEFAULT 1,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  closed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO user (email, name, password, role, mobile, location, wechatid, avatar) VALUES("nonocast@gmail.com", "Hui", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", TRUE, "13817122600", "上海", "oLwYvwad8ofhOc9yHK1WDbfH0mms", "http://wx.qlogo.cn/mmopen/KetjXWSVpptEZctGOzooiaFJCY3d6QV4icImfTyQVEiaibdTrMWlzjE0Bial1LKMkDBaPztNhf5EE9YgsFzBDH6LGDA/0");
INSERT INTO user (email, name, password, role) VALUES("fyx@gmail.com", "fangyx", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", FALSE);
INSERT INTO user (email, name, password, role) VALUES("gexi7n@gmail.com", "Ge", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", TRUE);
INSERT INTO user (email, name, password, role) VALUES("naodaixiaoxiao@qq.com", "李毓洁", "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC", FALSE);


SET @email = "naodaixiaoxiao@qq.com";
SET @user = (select id from user where email=@email);
SET @name = (select name from user where email=@email);

INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "装修房子");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "购买松下木门");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "确定阳台装修风格");


SET @email = "gexi7n@gmail.com";
SET @user = (select id from user where email=@email);
SET @name = (select name from user where email=@email);

INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "设计todolist web application交互界面");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "学习TypeScript + React");

SET @email = "nonocast@gmail.com";
SET @user = (select id from user where email=@email);
SET @name = (select name from user where email=@email);

INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "上周我们面试实习生,清华的、剑桥的全被我们刷下来了");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "只留下一个没上过大学的.");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "因为她夸我长的好看.");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "如今这个社会敢说实话的人已经不多了.");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "好吧,上面是我意淫.");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "她根本没夸我好看.");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "我问了她连个问题.");

INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "<h1>TEST HTML</h1>");
INSERT INTO task (belongs_to, belongs_to_name, belongs_to_email, status, category, content) VALUES(@user, @name, @email, 0, 0, "<script>alert('test javascript');</script>");


DELIMITER ;;

DROP PROCEDURE IF EXISTS printf;
CREATE PROCEDURE printf(thetext TEXT)
  BEGIN
    select thetext as ``;
  END;
;;


DROP PROCEDURE IF EXISTS create_users;
CREATE PROCEDURE create_users(count INTEGER)
  BEGIN

    SET @i = 1;
    WHILE @i < count+1 DO
      SET @email = concat("user_", @i, "@gmail.com");
      SET @username = concat("user_", @i);
      INSERT INTO user (email, name, password) VALUES(@email, @username, "$2a$10$NvR/yNovADZ2OQiipGUS7On7G0mKDknKzh5oWqGznv.qUIKitN/SC");

      SET @i = @i + 1;
    END WHILE;

  END;
;;


DROP PROCEDURE IF EXISTS create_tasks;
CREATE PROCEDURE create_tasks(count INTEGER)
  BEGIN

    SET @i = 1;
    WHILE @i < count+1 DO
      SET @content = concat("task item ", @i);
      INSERT INTO task (belongs_to, belongs_to_name, status, category, content) VALUES(@user, @name, 0, 0, @key);

      SET @i = @i + 1;
    END WHILE;

  END;
;;

DELIMITER ;


# CALL create_users(800);
# CALL create_tasks(500);
