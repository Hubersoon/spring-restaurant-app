delete
from app.meals;

delete
from app.restaurants;

insert into app.restaurants (id, name, address, type) values ('9bca7e30-3172-11eb-adc1-0242ac120002', 'Gregory Burger','Kielce' , 'AMERICAN');
insert into app.restaurants (id, name, address, type) values ('9bca7c0a-3172-11eb-adc1-0242ac120002', 'Pizza Italiana','Milano', 'ITALIAN');
insert into app.restaurants (id, name, address, type) values ('8139164e-3172-11eb-adc1-0242ac120002', 'Le French Food','Paris', 'FRENCH');
insert into app.restaurants (id, name, address, type) values ('8139187e-3172-11eb-adc1-0242ac120002', 'Kebab & Falafel','Warsaw','TURKISH');
insert into app.restaurants (id, name, address, type) values ('81391996-3172-11eb-adc1-0242ac120002', 'Sushi Osaka','Boston','ASIAN');


insert into app.meals (id, name, price, restaurant_id) values ('8139164e-3172-11eb-adc1-0242ac120002','Big Beef Burger', 31, '9bca7e30-3172-11eb-adc1-0242ac120002');
insert into app.meals (id, name, price, restaurant_id) values ('81391a72-3172-11eb-adc1-0242ac120002','Chcicken Burger', 28, '9bca7e30-3172-11eb-adc1-0242ac120002');
insert into app.meals (id, name, price, restaurant_id) values ('81391cd4-3172-11eb-adc1-0242ac120002','Pizza Margherita',39, '9bca7c0a-3172-11eb-adc1-0242ac120002');
insert into app.meals (id, name, price, restaurant_id) values ('8139187e-3172-11eb-adc1-0242ac120002','Snails with salt', 45, '8139164e-3172-11eb-adc1-0242ac120002');
insert into app.meals (id, name, price, restaurant_id) values ('81391ffe-3172-11eb-adc1-0242ac120002','Lamb kebab', 28, '8139187e-3172-11eb-adc1-0242ac120002');
insert into app.meals (id, name, price, restaurant_id) values ('82391b4e-3172-11eb-adc1-0242ac120002','Falafel', 24, '8139187e-3172-11eb-adc1-0242ac120002');
insert into app.meals (id, name, price, restaurant_id) values ('81391b4e-3242-11eb-adc1-0242ac120002','Sushi nigiri set', 42, '81391996-3172-11eb-adc1-0242ac120002');
insert into app.meals (id, name, price, restaurant_id) values ('81391b4e-3172-11eb-adc1-0242ac120002','Fried shrimp', 24, '81391996-3172-11eb-adc1-0242ac120002');