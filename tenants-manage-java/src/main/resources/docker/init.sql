CREATE DATABASE default_db;
CREATE DATABASE tenant_dev;
\c tenant_dev;

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO users (name, username, password)
SELECT 'Dev User', 'dev_user', '$2a$10$ADj2i1Jh63kHHvRI8Plmhu5yIVsw9RHXgCENbRc/LkXj6fjikCptW'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'dev_user');

CREATE DATABASE tenant_prod;
\c tenant_prod;

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO users (name, username, password)
SELECT 'Prod User', 'prod_user', '$2a$10$ADj2i1Jh63kHHvRI8Plmhu5yIVsw9RHXgCENbRc/LkXj6fjikCptW'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'prod_user');
