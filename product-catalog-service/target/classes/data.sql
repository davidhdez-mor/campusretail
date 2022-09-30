INSERT INTO categories(category) VALUES ('uncategorized');
INSERT INTO categories(category) VALUES ('monitors');
INSERT INTO categories(category) VALUES ('keyboards');
INSERT INTO categories(category) VALUES ('mice');

INSERT INTO products(availability, description, price, product_name)
VALUES (1, 'Monitor de 25 pulgadas 144 Hz', 200, 'Monitor Gamer LG');

INSERT INTO products(availability, description, price, product_name)
VALUES (1, 'Pro keyboard for true gamers', 80, 'Corsair Gamer Keyboard');

INSERT INTO products(availability, description, price, product_name)
VALUES (1, 'The mouse for truly dedicated gamers', 40, 'BENQ Pro Mouse');


INSERT INTO products_category (products_id, category_id)
VALUES (1, 2),
       (2, 3),
       (3, 4);
