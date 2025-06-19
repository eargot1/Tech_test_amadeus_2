CREATE TABLE pets (
                               id integer PRIMARY KEY AUTO_INCREMENT,
                               name varchar(15) NOT NULL,
                               age integer,
                               species varchar(8) NOT NULL,
                               status varchar(20) NOT NULL,
                               entry_date timestamp
);

CREATE TABLE users (
                               id integer PRIMARY KEY AUTO_INCREMENT,
                               name varchar(15) NOT NULL,
                               role varchar(5) NOT NULL,
                               pass varchar(20) NOT NULL
);

CREATE TABLE adoptions (
                               id integer PRIMARY KEY AUTO_INCREMENT,
                               pet_id integer NOT NULL,
                               user_id integer NOT NULL,
                               adoption_date varchar(15) NOT NULL,
                               status: varchar(15) NOT NULL
);



