CREATE TABLE customer
(
    id            BIGINT       NOT NULL,
    name          VARCHAR(500) NOT NULL,
    date_of_birth date         NOT NULL,
    password      VARCHAR(500) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE account
(
    id          BIGINT  NOT NULL,
    customer_id BIGINT  NOT NULL,
    balance     DECIMAL NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_customer UNIQUE (customer_id);

ALTER TABLE account
    ADD CONSTRAINT FK_ACCOUNT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

CREATE TABLE email_data
(
    id          BIGINT       NOT NULL,
    customer_id BIGINT       NOT NULL,
    email       VARCHAR(200) NOT NULL,
    CONSTRAINT pk_emaildata PRIMARY KEY (id)
);

ALTER TABLE email_data
    ADD CONSTRAINT FK_EMAILDATA_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

CREATE TABLE phone_data
(
    id           BIGINT       NOT NULL,
    customer_id  BIGINT       NOT NULL,
    phone_number VARCHAR(13) NOT NULL,
    CONSTRAINT pk_phonedata PRIMARY KEY (id)
);

ALTER TABLE phone_data
    ADD CONSTRAINT FK_PHONEDATA_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);