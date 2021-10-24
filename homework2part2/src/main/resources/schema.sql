--for h2 database without auth_user_group
CREATE TABLE secret (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    secret_id VARCHAR(128) NOT NULL UNIQUE,
    secret_data VARCHAR(128) NOT NULL
);
