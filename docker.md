
CREATE TABLE timezones (
  id UUID NOT NULL PRIMARY KEY,
  country_id UUID NOT NULL,
  timezone_name VARCHAR(100) NOT NULL,
  create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_op_id UUID NOT NULL
);


CREATE TABLE continents (
  id UUID NOT NULL PRIMARY KEY,
  continent_name VARCHAR(100) NOT NULL,
  region_name VARCHAR(100),
  create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_op_id UUID NOT NULL
);

CREATE TABLE countries (
  id UUID NOT NULL PRIMARY KEY,
  continent_id UUID NOT NULL,
  country_name VARCHAR(100) NOT NULL,
  create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_op_id UUID NOT NULL
);

CREATE TABLE cities (
  id UUID NOT NULL PRIMARY KEY,
  timezones_id UUID NOT NULL,
  city_name VARCHAR(100) NOT NULL,
  highlighted BOOLEAN NOT NULL DEFAULT FALSE,
  create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_op_id UUID NOT NULL
);

CREATE TABLE interest_groups (
  id UUID PRIMARY KEY,
  group_name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_op_id UUID NOT NULL
);

CREATE TABLE interest_group_cities (
  id UUID PRIMARY KEY,
  group_id UUID NOT NULL,
  city_id UUID NOT NULL,
  create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_op_id UUID NOT NULL
);


CREATE TABLE users (
  id UUID NOT NULL PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  name VARCHAR(100) ,
  birthday DATE,
  role VARCHAR(20) NOT NULL,
  create_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
  last_updated_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
)

CREATE TABLE user_interest_groups (
id UUID PRIMARY KEY,
group_name VARCHAR(100) NOT NULL,
description VARCHAR(255),
create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
last_op_id UUID NOT NULL
);

CREATE TABLE user_interest_group_cities (
  id UUID PRIMARY KEY,
  user_group_id UUID NOT NULL,
  city_id UUID NOT NULL,
  create_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  last_op_id UUID NOT NULL
);

INSERT INTO users (id, email, password, name, birthday, role, create_timestamp, last_updated_timestamp)
VALUES (gen_random_uuid(), '', '', 'earth', '', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

123456789&Abc
da6a1dcd-5acf-4fe0-b391-a9bb991666df


//countries
INSERT INTO countries (id, continent_id, country_name, create_timestamp, last_updated_timestamp, last_op_id)
VALUES (gen_random_uuid(), '', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'da6a1dcd-5acf-4fe0-b391-a9bb991666df');

//city
INSERT INTO cities (id, country_id, city_name, highlighted, create_timestamp, last_updated_timestamp, last_op_id)
VALUES (gen_random_uuid(), '', '', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'da6a1dcd-5acf-4fe0-b391-a9bb991666df')


INSERT INTO interest_groups (id, group_name, description, create_timestamp, last_updated_timestamp, last_op_id)
VALUES (gen_random_uuid(),'', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'da6a1dcd-5acf-4fe0-b391-a9bb991666df')
