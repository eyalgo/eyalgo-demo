create table message_id_to_guest_id(
    message_id uuid not null,
    guest_id bigint not null,
    primary key (message_id)
);
