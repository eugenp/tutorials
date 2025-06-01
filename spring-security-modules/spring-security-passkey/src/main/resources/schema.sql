create table if not exists PASSKEY_USERS (
    id           long primary key not null auto_increment,
    external_id  varchar(128)     not null,
    name         varchar(255)     not null,
    display_name varchar(255)     null,
    unique (external_id),
    unique (name)
);



create table if not exists PASSKEY_CREDENTIALS (
    id                 long primary key not null auto_increment,
    user_id            long             not null references PASSKEY_USERS (id),
    label              varchar(255),
    credential_type    varchar(255)     not null,
    credential_id      varchar(4096)    not null,
    public_key_cose    text             not null,
    signature_count    long             not null default 0,
    uv_initialized     boolean          not null default false,
    transports         varchar(255)     not null default '',
    backup_eligible    boolean          not null default false,
    backup_state       boolean          not null default false,
    attestation_object text             null,
    last_used          timestamp        not null default current_timestamp,
    created            timestamp        not null default current_timestamp,
    unique (credential_id)
);