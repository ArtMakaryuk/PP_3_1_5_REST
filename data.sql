insert into roles (name) values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, password, last_name, age, email) values
    ('admin', '$2a$12$Vm/Ep5.R9x4lk8bMmggBteTFEfSIMXqnKQdPGdXfwoorRg9JhhMHu', 'admin', '30', 'admin@mail.ru');

insert into user_roles (user_id, role_id) values (1, 2);