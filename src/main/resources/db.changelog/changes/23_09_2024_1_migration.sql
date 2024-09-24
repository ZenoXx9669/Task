create table if not exists users (
    id serial primary key ,
    full_name varchar,
    email varchar,
    password varchar
);

INSERT INTO users (full_name, email, password)
VALUES ('Olzhas Jakenov', 'olzhasjakenov@gmail.com', '$2a$12$MlUsLXrS5P8NG5iY6aY4ju5KdA09ROXpNLekte0aF0Rkd5OskIII6');

INSERT INTO users (full_name, email, password)
VALUES ('Zhalgas Mereyov', 'zhalgas@gmail.com', '$2a$12$XKTGgUTuwZPxC/xpFy0emepXzapA8q2nMq4cRyJ.L1qxbYvSEFy8m');

INSERT INTO users (full_name, email, password)
VALUES ('Turar Esenov', 'esenov@gmail.com', '$2a$12$0eckKZud2ULKy8e.dCM8VOBwuDvKW7DqeotdXLCRfZ1jjbS/kGXgq');




