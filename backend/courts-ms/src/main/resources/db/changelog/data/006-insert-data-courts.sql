-- liquibase formatted sql

-- changeset timur:8
INSERT INTO courts (lat, lon, court_type, city_id, court_info_id) VALUES
(55.7558, 37.6176, 'TENNIS', 1, (SELECT id FROM court_info WHERE title = 'Теннисный корт ЦПКиО')),
(55.7483, 37.6176, 'BASKETBALL', 1, (SELECT id FROM court_info WHERE title = 'Баскетбольная площадка в парке')),
(59.9343, 30.3351, 'VOLLEYBALL', 2, (SELECT id FROM court_info WHERE title = 'Пляжный волейбол у Невы')),
(55.7512, 37.6184, 'FOOTBALL', 1, (SELECT id FROM court_info WHERE title = 'Мини-футбольное поле'));
