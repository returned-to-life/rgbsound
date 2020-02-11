CREATE USER 'rgbuser'@'localhost' IDENTIFIED BY 'rgbpass';

CREATE SCHEMA rgbdb;

GRANT ALL PRIVILEGES ON rgbdb.* TO 'rgbuser'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE coverage_map
(
    id IDENTITY NOT NULL,
    freq_start DOUBLE,
    freq_end DOUBLE,
    step_factor DOUBLE,
    step_width INT,
    PRIMARY KEY (id)
);

CREATE TABLE user
(
    id IDENTITY NOT NULL,
    login VARCHAR (100),
    password VARCHAR (100),
    PRIMARY KEY (id)
);

CREATE TABLE coverage_done
(
    id IDENTITY NOT NULL,
    coverage_map_id INT NOT NULL,
    step_index_a INT,
    step_index_b INT,
    user_id INT,
    timestamp TIMESTAMP,
    comment VARCHAR (2040),
    CONSTRAINT fk_coverage_done_map FOREIGN KEY (coverage_map_id) REFERENCES coverage_map(id),
    CONSTRAINT fk_coverage_done_user FOREIGN KEY (user_id) REFERENCES user(id),
    PRIMARY KEY (id)
);

CREATE TABLE found
(
    id IDENTITY NOT NULL,
    coverage_done_id INT NOT NULL,
    freq_a DOUBLE,
    freq_b DOUBLE,
    phase_matters BOOLEAN,
    CONSTRAINT fk_found_done FOREIGN KEY (coverage_done_id) REFERENCES coverage_done(id),
    PRIMARY KEY (id)
);
