--for h2 database without auth_user_group
--CREATE TABLE user_details (
--    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    username VARCHAR(128) NOT NULL UNIQUE,
--    password VARCHAR(128) NOT NULL,
--    user_role VARCHAR(128) NOT NULL
--);

--for h2 database with auth_user_group
CREATE TABLE user_details (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL
);

--for granted authority
CREATE TABLE auth_user_group (
    auth_user_group_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(128) NOT NULL,
    auth_group VARCHAR(128) NOT NULL,
    CONSTRAINT user_details_auth_user_group_fk FOREIGN KEY (username) REFERENCES user_details(username),
    UNIQUE (username, auth_group)
);

--for brute force protection
CREATE TABLE login_attempt (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(128) NOT NULL,
    attempt_count INTEGER NOT NULL,
    is_blocked BOOLEAN
);
