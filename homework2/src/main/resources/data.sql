--for h2 database without encryption
--INSERT INTO user_details (username, password, user_role)
--VALUES ('admin@email.com', 'admin1', 'ADMIN');
--
--INSERT INTO user_details (username, password, user_role)
--VALUES ('user@email.com', 'password1', 'USER');

--for h2 database with encryption
INSERT INTO user_details (username, password)
VALUES ('admin@email.com', '$2a$10$BKDQfk1CRGH4TVTf9ZrnSej1cIFRHUZDLJ.QgwQ7eqOmstB5tHtRi');

INSERT INTO user_details (username, password)
VALUES ('user@email.com', '$2a$10$F58Yld4LsykSVM82B9P0g./01PW56vwJtat3bDxeISOtMqHOmT41m');

INSERT INTO auth_user_group (username, auth_group)
VALUES('admin@email.com', 'USER');

INSERT INTO auth_user_group (username, auth_group)
VALUES('admin@email.com', 'ADMIN');

INSERT INTO auth_user_group (username, auth_group)
VALUES('user@email.com', 'USER');