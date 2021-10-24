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

INSERT INTO user_details (username, password, user_role)
VALUES ('admin@email.com', 'admin2', 'ADMIN');

INSERT INTO user_details (username, password, user_role)
VALUES ('user@email.com', 'password2', 'USER');