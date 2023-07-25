-- id: uuid primary key
-- email: varchar(40) not null unique
-- password: varchar (256) not null
-- company: varchar(40)

create table users(
    id uuid primary key,
    name varchar(100) not null,
    email varchar(40) not null unique,
    password varchar(256) not null,
    company varchar(40)
);