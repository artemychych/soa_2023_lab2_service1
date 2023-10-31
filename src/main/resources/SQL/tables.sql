create table ticket (
                      id serial primary key,
                      name text not null,
                      coordinates_x int not null,
                      coordinates_y int not null,
                      creation_date timestamp without time zone,
                      price double precision,
                      type character varying(255) not null,
                      person_id bigint,
                      person_weight bigint,
                      person_hair_color character varying(255),
                      person_location_x double precision,
                      person_location_y bigint,
                      person_location_z double precision

);