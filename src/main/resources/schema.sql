CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.restaurants

(
  id UUID default random_uuid(),
  name CHARACTER VARYING(70) NOT NULL,
  address CHARACTER VARYING(200) NOT NULL,
  type CHARACTER VARYING(15) NOT NULL,
  CONSTRAINT restaurant_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS app.meals
(
  id UUID NOT NULL PRIMARY KEY,
  name CHARACTER VARYING(50) NOT NULL,
  price NUMERIC NOT NULL,
  restaurant_id UUID,
  CONSTRAINT restaurant_id FOREIGN KEY (restaurant_id) REFERENCES app.restaurants (id)
);
