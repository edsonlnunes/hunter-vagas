-- id: uuid primary key
-- id_candidate: uuid not null fk
-- id_job: uuid not null fk
-- status: varchar(20) not null
-- applied_date: date not null

create table candidates_jobs(
   id uuid primary key,
   id_candidate uuid not null references users(id),
   id_job uuid not null references jobs(id),
   status varchar(20) not null,
   applied_date date not null
);