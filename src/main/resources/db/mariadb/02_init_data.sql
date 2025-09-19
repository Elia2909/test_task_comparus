INSERT INTO user_table(ldap_login, name, surname)
VALUES ('example-user-id-3', 'Vasyl', 'Vasylenko')
ON DUPLICATE KEY UPDATE name = 'Vasyl';

INSERT INTO user_table(ldap_login, name, surname)
VALUES ('example-user-id-4', 'Taras', 'Tarasenko')
ON DUPLICATE KEY UPDATE name = 'Taras';