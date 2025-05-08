CREATE TABLE IF NOT EXISTS tenants
(
    domain            VARCHAR(50) PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    url_database      VARCHAR(255) NOT NULL,
    user_database     VARCHAR(255) NOT NULL,
    password_database VARCHAR(255) NOT NULL
);

INSERT INTO tenants (domain, name, password_database, url_database, user_database)
SELECT 'dev', 'Dev', 'root', 'jdbc:postgresql://tenants-db:5432/tenant_dev', 'postgres'
WHERE NOT EXISTS (SELECT 1 FROM tenants WHERE domain = 'dev');

INSERT INTO tenants (domain, name, password_database, url_database, user_database)
SELECT 'prod', 'Prod', 'root', 'jdbc:postgresql://tenants-db:5432/tenant_prod', 'postgres'
WHERE NOT EXISTS (SELECT 1 FROM tenants WHERE domain = 'prod');