create table management.users
(
    id       serial
        constraint users_pk
            primary key,
    name     varchar not null,
    surname  varchar,
    role     varchar,
    email    varchar not null,
    password varchar not null
);
create unique index users_email_ux on management.users (email);