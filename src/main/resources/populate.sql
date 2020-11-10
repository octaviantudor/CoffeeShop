use coffeeshop2;
insert into ingredient (name, price) values('CINNAMON',2),('HONEY',1),
('HOT WATER', 2), ('MILK FOAM',2),('STEAMED MILK',2),('BLACK SUGAR',2),
('SUGAR',2),('BLACK COFFEE',4),('ESPRESSO SHOT',4);

insert into stock_ingredients (stock_quantity, ingredient_id) VALUES (60,1),(60,2),(60,3),(60,4),(60,5),(60,6)
,(60,7),(60,8),(60,9);

insert into coffee (name,price) values ('ESPRESSO',8),('CAPPUCCINO',10),('COFFEE LATE',10),('COFFEE MIEL',9), ('MACCHIATO',10);

insert into recipe_ingredient (price,quantity,ingredient_id,coffee_id) values
(8,2,9,1),
(4,1,9,2),(2,1,5,2),(4,2,4,2),
(4,1,9,3),(4,2,5,3),(2,1,4,3),
(4,1,8,4),(1,1,2,4),(2,1,1,4),(2,1,5,4),
(8,2,9,5),(2,1,4,5);