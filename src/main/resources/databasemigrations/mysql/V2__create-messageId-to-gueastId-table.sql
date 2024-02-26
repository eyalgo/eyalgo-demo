create table message_id_to_guest_id(
    message_id binary(16) not null,
    guest_id bigint not null,
    primary key (message_id)
);
