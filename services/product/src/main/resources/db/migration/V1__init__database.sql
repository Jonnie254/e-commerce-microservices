create table if not exists category
(
    id integer not null primary key,
    description varchar(255),
    name varchar(255)
    );

create table if not exists product
(
    id integer not null primary key,
    description varchar(255),
    name varchar(255),
    available_quantity double precision not null,
    price numeric(38, 2) not null,
    category_id integer constraint fk1bjejjcjjnsj references category
    );

create sequence category_seq increment by 50;
create sequence product_seq increment by 50;
