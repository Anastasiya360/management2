create table management.tasks
(
    id       serial
        constraint tasks_pk
            primary key,
    title     varchar not null,
    description  varchar not null,
    status    varchar not null,
    priority varchar not null,
    user_id_author integer not null,
    constraint tasks_user_id_author_fk
        foreign key (user_id_author) references management.users (id),
    user_id_executor integer,
    constraint tasks_user_id_executor_fk
        foreign key (user_id_executor) references management.users (id)
);
create index tasks_user_id_author_ix on management.tasks (user_id_author);
create index tasks_user_id_executor_ix on management.tasks (user_id_executor);