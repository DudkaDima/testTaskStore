insert into product(id, name_of_product, price, is_available) values(1, 'iphone 11', 20000.99, true);
insert into product(id, name_of_product, price, is_available) values(2, 'iphone 12', 30000, true);
insert into roles values(1, 'ADMIN');
insert into roles values(2, 'CLIENT');

insert into users(id, firstname, surname, phone, username, email, password, role_id) values(1, 'Jenya', 'Jenya', 'AdminPhone', 'admin' , 'AdminEmail', '$2a$10$S0l3UMh9TaCySwcdkrRQvetMU5sKQghYRKsUMOhXu8iYvYqjqjrya', 1);
insert into users(id, firstname, surname, phone, username, email, password, role_id) values(2, 'dima', 'dudka', 'phone', 'user', 'email', '$2a$10$kWwqG5XPj2Jf2IMp2j.duOaB41wi46mIgLBhM4OJpJO1YhuoSzz6C', 2);
