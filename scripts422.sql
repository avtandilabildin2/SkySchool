create table car(
    id bigserial primary key ,
    brand varchar(50) not null ,
    model varchar(50) not null ,
    price numeric(12,2) not null
);
create table person(
    id bigserial primary key ,
    name varchar(75) not null ,
    age bigint not null ,
    has_driver_licence boolean not null ,
    car_id bigint references car(id)
);