
INSERT INTO cart (total_amount) VALUES (0.0);
INSERT INTO cart (total_amount) VALUES (0.0);
insert into user (first_name, last_name, cart_id) values ('sagar', 'varsani', (SELECT id FROM cart LIMIT 1));
insert into user (first_name, last_name, cart_id) values ('StarTech', 'Seller', (SELECT id FROM cart ORDER BY id DESC LIMIT 1));