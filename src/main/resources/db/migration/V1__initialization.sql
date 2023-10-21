-- Table: service.specialization
DROP SEQUENCE IF EXISTS service.specialization_id_seq;

CREATE SEQUENCE IF NOT EXISTS service.specialization_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP TABLE IF EXISTS service.specialization;

CREATE TABLE IF NOT EXISTS service.specialization (
    id bigint NOT NULL DEFAULT nextval('specialization_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT specialization_pkey PRIMARY KEY (id)
);

-- Table: service.qualification

DROP SEQUENCE IF EXISTS service.qualification_id_seq;

CREATE SEQUENCE IF NOT EXISTS service.qualification_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP TABLE IF EXISTS service.qualification;

CREATE TABLE IF NOT EXISTS service.qualification
(
    maximal_defective_products_percentage double precision,
    id bigint NOT NULL DEFAULT nextval('qualification_id_seq'::regclass),
    minimal_manufactured_products bigint,
    specialization_id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT qualification_pkey PRIMARY KEY (id),
    CONSTRAINT fkn2k8ara9taupv4667fmx3k3se FOREIGN KEY (specialization_id)
        REFERENCES service.specialization (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Table: service.worker


DROP SEQUENCE IF EXISTS service.worker_id_seq;

CREATE SEQUENCE IF NOT EXISTS service.worker_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP TABLE IF EXISTS service.worker;

CREATE TABLE IF NOT EXISTS service.worker
(
    defective_products_count bigint,
    id bigint NOT NULL DEFAULT nextval('worker_id_seq'::regclass),
    manufactured_products_count bigint,
    qualification_id bigint NOT NULL,
    audit_results character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    surname character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT worker_pkey PRIMARY KEY (id),
    CONSTRAINT fk3xfx0ldyf8ia0e4kahi16fqxf FOREIGN KEY (qualification_id)
        REFERENCES service.qualification (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Table: service.complaint

DROP SEQUENCE IF EXISTS service.complaint_id_seq;

CREATE SEQUENCE IF NOT EXISTS service.complaint_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP TABLE IF EXISTS service.complaint;

CREATE TABLE IF NOT EXISTS service.complaint
(
    id bigint NOT NULL DEFAULT nextval('complaint_id_seq'::regclass),
    worker_id bigint NOT NULL,
    content character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT complaint_pkey PRIMARY KEY (id),
    CONSTRAINT fkf268nerfld53w1lea4r6truma FOREIGN KEY (worker_id)
        REFERENCES service.worker (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);