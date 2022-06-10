insert into user_category (name, discount_percentage)
values ('ADULT', 0),
       ('CHILD', 10),
       ('SENIOR', 15),
       ('STUDENT', 20);

insert into genre (name)
values ('THRILLER'),
       ('HORROR');

insert into room_type (name, seat_price)
values ('STANDARD', 2.5),
       ('PREMIUM', 6);

insert into city (name)
values ( 'Minsk' );

insert into movie (duration_in_minutes, name, description, price, age_rating, rating)
values (108, 'Test movie', 'Desc test', 2, 1, 10);

insert into movies_genres_link (movie_id, genre_id)
values (1, 1),
       (1, 2);

insert into cinema (name, address, city_id, email, phone)
values ('Test', 'Test address', 1, 'test@test', '+000000000');

insert into movie_room (capacity, room_type_id, cinema_id)
values (1, 1, 1);

insert into seat (row_number, place_number, movie_room_id)
values (1, 1, 1);

insert into users (first_name, last_name, user_name, email, phone, password, city_id, registration_date_time, category_id, role)
values ('A', 'B', 'AD', 'a@a', '4364364354', 'jfd2394ucf923urhcf', 1, '2022-01-08T12:30:00', 1, 1);

insert into event (start_date_time, movie_id, event_status, movie_room_id)
values ('2022-01-08T12:30:00', 1, 0, 1);

insert into orders (creation_date_time, overall_price, user_id, order_status, event_id)
values ('2022-01-08T12:30:00', 0.0, 1, 0, 1);

insert into weather (city_id, date_time, temperature, feels_like, pressure, humidity, min_temp, max_temp, visibility,
                     wind_speed, wind_degree, cloudiness)
values ( 1, '2022-01-08T12:30:00', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
       ( 1, '2022-03-08T12:30:00', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);