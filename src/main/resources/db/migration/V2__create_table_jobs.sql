-- id: uuid primary key
-- id_recruiter: uuid not null fk
-- description: text not null
-- company: varchar(40)
-- enable: bool not null default true
-- limit_date: date not null
-- max_candidates: int

create table jobs(
    id uuid primary key,
    id_recruiter uuid not null references users(id),
    description text not null,
    company varchar(40),
    enable boolean not null default true,
    limit_date date not null,
    max_candidates int
);