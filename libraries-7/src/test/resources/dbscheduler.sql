create table scheduled_tasks (
                                 task_name varchar(100),
                                 task_instance varchar(100),
                                 task_data blob,
                                 execution_time TIMESTAMP WITH TIME ZONE,
                                 picked BIT,
                                 picked_by varchar(50),
                                 last_success TIMESTAMP WITH TIME ZONE,
                                 last_failure TIMESTAMP WITH TIME ZONE,
                                 consecutive_failures INT,
                                 last_heartbeat TIMESTAMP WITH TIME ZONE,
                                 version BIGINT,
                                 priority SMALLINT,
                                 PRIMARY KEY (task_name, task_instance)
)