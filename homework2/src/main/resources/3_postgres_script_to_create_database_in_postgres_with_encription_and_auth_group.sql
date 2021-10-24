--for PostgreSQL
CREATE DATABASE homework2
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE homework2 TO postgres;

-- DROP TABLE user_details;

CREATE TABLE IF NOT EXISTS user_details (
    user_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
	CONSTRAINT user_details_pkey PRIMARY KEY (user_id)
);

-- DROP TABLE auth_user_group;

CREATE TABLE IF NOT EXISTS auth_user_group
(
    auth_user_group_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username VARCHAR(128) NOT NULL,
    auth_group VARCHAR(128) NOT NULL,
    CONSTRAINT auth_user_group_pkey PRIMARY KEY (auth_user_group_id),
    CONSTRAINT auth_user_group_username_auth_group_key UNIQUE (username)
        INCLUDE(auth_group),
    CONSTRAINT user_details_auth_user_group_fk FOREIGN KEY (username)
        REFERENCES user_details (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--here is password "adminpsw"
INSERT INTO user_details (username, password, user_role)
VALUES ('admin@email.com', '$2a$10$dgu7mz.5j5mo2NZs7hqYNuEopaTKBjCtg4eaY6zq28mU.E/uJ5HM6');

--here is password "userpsw"
INSERT INTO user_details (username, password, user_role)
VALUES ('user@email.com', '$2a$10$rr2ra1LPViPgEPCeSDuFLe6uZ1sgsQerkQiud23RutflfHKeSMvHy');

INSERT INTO auth_user_group (username, auth_group)
VALUES('admin@email.com', 'USER');

INSERT INTO auth_user_group (username, auth_group)
VALUES('admin@email.com', 'ADMIN');

INSERT INTO auth_user_group (username, auth_group)
VALUES('user@email.com', 'USER');