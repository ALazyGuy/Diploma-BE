create table if not exists UserEntity(
    id integer primary key auto_increment,
    username varchar(20) not null unique,
    password_hash varchar(100) not null
);