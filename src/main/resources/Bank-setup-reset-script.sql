drop table if exists users;
drop table if exists accounts;
drop table if exists user_account_join;

CREATE table users(
    user_id integer PRIMARY KEY autoincrement,
	username text,
	password text
);

CREATE table accounts(
	account_id integer PRIMARY KEY autoincrement,
	account_type text DEFAULT('Checking'),
	account_nickname text,
	account_balance float DEFAULT(0.0)
);

CREATE TABLE user_account_join(
    user_id integer NOT NULL REFERENCES users(user_id),
    account_id integer NOT NULL REFERENCES accounts(account_id)
);

INSERT into users(username, password) values ('admin', '1234');
INSERT INTO users(username, password) VALUES ('LetsHoldUp', 'TheBestPasswordEver');

INSERT INTO accounts(account_nickname) VALUES ('testing account');
INSERT INTO accounts(account_balance) VALUES (1000000.43);
INSERT INTO accounts(account_nickname, account_balance) VALUES ('Admin''s real account don''t delete', 3.50);
INSERT INTO accounts(account_balance) VALUES (1000000.43, 'Joint account test');

INSERT INTO user_account_join(1, 1);
INSERT INTO user_account_join(1, 3);
INSERT INTO user_account_join(1, 4);
INSERT INTO user_account_join(2, 2);
INSERT INTO user_account_join(2, 4);

