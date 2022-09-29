INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO MESSAGE (date_time, description, user_id)
VALUES (CURRENT_TIMESTAMP , 'User message 1', 1),
       ('2020-01-30 13:00:00', 'User message 2', 1),
       ('2020-01-30 20:00:00', 'User message 3', 1),
       ('2020-01-31 0:00:00', 'User message 4', 1),
       ('2020-01-31 10:00:00', 'User message 5', 1),
       ('2020-01-31 13:00:00', 'User message 6', 1),
       ('2020-01-31 20:00:00', 'User message 7', 1),
       ('2020-01-31 20:00:00', 'User message 8', 1),
       ('2020-01-31 20:00:00', 'User message 9', 1),
       ('2020-01-31 20:00:00', 'User message 10', 1),
       ('2020-01-31 14:00:00', 'Admin message 1', 2),
       ('2020-01-31 21:00:00', 'Admin message 2', 2);