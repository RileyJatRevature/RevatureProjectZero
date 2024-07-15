drop table if exists users;
drop table if exists accounts;

CREATE table users(
    user_id integer PRIMARY KEY autoincrement,
	username text,
	password text
);

CREATE table accounts(
	account_id integer PRIMARY KEY autoincrement,
	user_id integer NOT NULL REFERENCES users(user_id),
	account_type text DEFAULT('Checking'),
	account_nickname text,
	account_balance float DEFAULT(0.0)
);

INSERT into users(username, password) values ('admin', '1234');
INSERT INTO users(username, password) VALUES ('TheBestAccountEver', 'TheBestPasswordEver');

INSERT INTO accounts(user_id,account_nickname) VALUES (1, 'testing account');
INSERT INTO accounts(user_id,account_balance) VALUES (2, 1000000.43);
INSERT INTO accounts(user_id,account_nickname, account_balance) VALUES (1, 'Admin''s real account don''t delete', 3.50);
INSERT INTO accounts(user_id, account_balance) VALUES (2, 1000000.43);

