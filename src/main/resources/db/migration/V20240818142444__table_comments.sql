create table management.comments
(
    id       serial
        constraint comments_pk
            primary key,
    user_id    integer not null,
    constraint comments_user_id_fk
        foreign key (user_id) references management.users (id),
    description  varchar not null,
    date_create    date,
    tasks_id    integer not null,
    constraint comments_tasks_id_fk
        foreign key (tasks_id) references management.tasks (id)
);
create index comments_user_id_ix on management.comments (user_id);
create index comments_tasks_id_ix on management.comments (tasks_id);