drop table if exists users;
drop table if exists accounts;

CREATE table users(
    user_id integer PRIMARY KEY autoincrement,
	username text,
	password text
);

INSERT into users(username, password) values ('admin', '1234');

CREATE table accounts(
	account_id integer PRIMARY KEY autoincrement,
	user_id integer NOT NULL REFERENCES users(user_id),
	account_type text DEFAULT('checking'),
	account_nickname text,
	account_balance float DEFAULT(0.0)
);

INSERT INTO accounts(user_id, account_nickname) VALUES (1, 'testing account');