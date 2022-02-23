DROP TABLE IF EXISTS season_pass;
DROP TABLE IF EXISTS gyms;

CREATE TABLE gyms
(
    gym_id SERIAL PRIMARY KEY,
    address VARCHAR(255),
    gym_num INTEGER,
    open_time varchar(255)
);

CREATE TABLE season_pass
(
    user_id SERIAL PRIMARY KEY,
    gym_id BIGINT REFERENCES gyms,
    full_name VARCHAR(255),
    duration_m INTEGER
);


