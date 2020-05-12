
CREATE TYPE gender AS ENUM ('male', 'female');

CREATE TABLE "patients" (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(127) NOT NULL,
    gender gender NOT NULL,
    dob date NOT NULL,
    address varchar(255),
    oms_number VARCHAR(16) NOT NULL UNIQUE,
    updated_at timestamp,
    created_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT oms_number_check CHECK (oms_number ~ $$^\d{16}\Z$$)
);

CREATE FUNCTION to_gender(text) RETURNS gender
    AS 'select $1::gender'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;

