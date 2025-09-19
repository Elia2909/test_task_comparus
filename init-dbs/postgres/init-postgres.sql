DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'admin') THEN
            CREATE ROLE admin LOGIN PASSWORD 'admin';
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'comparus') THEN
            CREATE DATABASE comparus OWNER admin ENCODING 'UTF8';
        END IF;
    END
$$;

ALTER SYSTEM SET TimeZone = 'Europe/Kiev';
SELECT pg_reload_conf();