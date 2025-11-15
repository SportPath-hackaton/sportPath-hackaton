-- liquibase formatted sql

-- changeset timur:7
INSERT INTO court_info (lat, lon, address, rating, type, title, city_id, photo_url, paid, description) VALUES
(55.7558, 37.6176, 'ул. Теннисная, д. 1, ЦПКиО, Москва', 4.5, 'TENNIS', 'Теннисный корт ЦПКиО', 1, 'https://example.com/photo1.jpg', FALSE, 'Открытый теннисный корт, 2 корта'),
(55.7483, 37.6176, 'ул. Спортивная, д. 10, Москва', 4.7, 'BASKETBALL', 'Баскетбольная площадка в парке', 1, 'https://example.com/photo2.jpg', FALSE, 'Уличная баскетбольная площадка, резиновое покрытие'),
(59.9343, 30.3351, 'Набережная, д. 5, Санкт-Петербург', 4.3, 'VOLLEYBALL', 'Пляжный волейбол у Невы', 2, 'https://example.com/photo3.jpg', FALSE, 'Песчаное покрытие, летом работает'),
(55.7512, 37.6184, 'ул. Футбольная, д. 1, Москва', 4.8, 'FOOTBALL', 'Мини-футбольное поле', 1, 'https://example.com/photo4.jpg', TRUE, 'Искусственная трава, крытое поле, раздевалки');
