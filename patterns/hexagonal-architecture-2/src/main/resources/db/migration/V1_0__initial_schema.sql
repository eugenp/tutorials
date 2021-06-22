create table appointments
(
    id                 varchar(36) not null,
    appointment_time   date,
    requester_email    varchar(255),
    created_date       date,
    last_modified_date date,
    primary key (id)
);