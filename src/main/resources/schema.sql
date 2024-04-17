-- TABLE FOR DATABASE
DROP TABLE IF EXISTS run;

CREATE TABLE IF NOT EXISTS run (
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    miles INT NOT NULL,
    location varchar(10) NOT NULL,
--    version INT,
    PRIMARY KEY (id)
);