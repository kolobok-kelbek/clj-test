
CREATE TYPE gender AS ENUM ('male', 'female');

CREATE TABLE "patients" (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(127) NOT NULL,
    gender gender NOT NULL,
    dob date,
    address varchar(255),
    oms_number VARCHAR(16) NOT NULL,
    CONSTRAINT oms_number_check CHECK (oms_number ~ $$^\d{16}\Z$$)
);

