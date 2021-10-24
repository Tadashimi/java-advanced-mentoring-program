--for PostgreSQL
CREATE DATABASE homework2
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE homework2 TO postgres;

CREATE TABLE user_details (
    user_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    user_role VARCHAR(128) NOT NULL,
	CONSTRAINT user_details_pkey PRIMARY KEY (user_id)
);

--here is password admin3
INSERT INTO user_details (username, password, user_role)
VALUES ('admin@email.com', '$2a$10$BKDQfk1CRGH4TVTf9ZrnSej1cIFRHUZDLJ.QgwQ7eqOmstB5tHtRi', 'ADMIN');

--here is password password3
INSERT INTO user_details (username, password, user_role)
VALUES ('user@email.com', '$2a$10$F58Yld4LsykSVM82B9P0g./01PW56vwJtat3bDxeISOtMqHOmT41m', 'USER');