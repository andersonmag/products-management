CREATE TABLE IF NOT EXISTS tenants
(
    domain            VARCHAR(50) PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    url_database      VARCHAR(255) NOT NULL,
    user_database     VARCHAR(255) NOT NULL,
    password_database VARCHAR(255) NOT NULL
);

INSERT INTO tenants (domain, name, password_database, url_database, user_database)
VALUES ('dev', 'Dev', 'root', 'jdbc:postgresql://tenants-db:5432/tenant_dev', 'postgres');
INSERT INTO tenants (domain, name, password_database, url_database, user_database)
VALUES ('prod', 'Prod', 'root', 'jdbc:postgresql://tenants-db:5432/tenant_prod', 'postgres');