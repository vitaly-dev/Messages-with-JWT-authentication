INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO MESSAGE (date_time, description, user_id)
VALUES ('2020-01-30 10:00:00', 'Завтрак', 1),
       ('2020-01-30 13:00:00', 'Обед', 1),
       ('2020-01-30 20:00:00', 'Ужин', 1),
       ('2020-01-31 0:00:00', 'Еда на граничное значение', 1),
       ('2020-01-31 10:00:00', 'Завтрак', 1),
       ('2020-01-31 13:00:00', 'Обед', 1),
       ('2020-01-31 20:00:00', 'Ужин', 1),
       ('2020-01-31 14:00:00', 'Админ ланч', 2),
       ('2020-01-31 21:00:00', 'Админ ужин', 2);