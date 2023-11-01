create table order_details (
      id bigserial not null,
       payment_status boolean,
         users_id int8,
         primary key (id)
     );

     create table orders (
        id  bigserial not null,
         quantity int8,
         product_id int8,
         orders_id int8,
         primary key (id)
     );
  
     
     create table product (
        id bigserial not null,
         is_available boolean,
         name_of_product varchar(255),
         price float8,
         primary key (id)
     );
  
     
     create table roles (
        id bigserial not null,
         name varchar(255),
         primary key (id)
     );
  
     
     create table users (
        id  bigserial not null,
         email varchar(255),
         firstname varchar(255),
         password varchar(255),
         phone varchar(255),
         surname varchar(255),
         username varchar(255),
         role_id int8,
         primary key (id)
     );
  
     
     alter table users 
        add constraint UK1drr8bhslhiv9m1s9inur8vff unique (email, phone);
  
     
     alter table order_details 
        add constraint FK4v9d5kqip2t51hdg6st3at4sl 
        foreign key (users_id) 
        references users;
  
     
     alter table orders 
        add constraint FK787ibr3guwp6xobrpbofnv7le 
        foreign key (product_id) 
        references product;
  
     
     alter table orders 
        add constraint FKj2svd0503yj257gqfrjyheu4i 
        foreign key (orders_id) 
        references order_details;
  
     
     alter table users 
        add constraint FKp56c1712k691lhsyewcssf40f 
        foreign key (role_id) 
        references roles;

        insert into product(name_of_product, price, is_available) values('iphone 11', 20000.99, true);
        insert into product(name_of_product, price, is_available) values('iphone 12', 30000.99, true);
        insert into roles values(1, 'ADMIN');
        insert into roles values(2, 'CLIENT');

        insert into users(id, firstname, surname, phone, username, email, password, role_id) values(1, 'Jenya', 'Jenya', 'AdminPhone', 'admin' , 'AdminEmail', '$2a$10$S0l3UMh9TaCySwcdkrRQvetMU5sKQghYRKsUMOhXu8iYvYqjqjrya', 1);
        insert into users(id, firstname, surname, phone, username, email, password, role_id) values(2, 'dima', 'dudka', 'phone', 'user', 'email', '$2a$10$kWwqG5XPj2Jf2IMp2j.duOaB41wi46mIgLBhM4OJpJO1YhuoSzz6C', 2);
