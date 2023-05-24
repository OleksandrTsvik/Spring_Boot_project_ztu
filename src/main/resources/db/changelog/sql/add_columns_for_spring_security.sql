alter table users
    add column enabled boolean not null;

insert into users(full_name, phone_number, email, password, enabled)
values ('Test disabled',
        '380000456789',
        'disabled@gmail.com',
           # password = 123123
        '$2a$10$qvdC1wQ88q3AkSFo1ORa7.25Ukq6cze8By/VlXdzs.C3dwN/JeghO',
        false);