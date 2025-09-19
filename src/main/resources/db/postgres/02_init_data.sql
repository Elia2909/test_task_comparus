INSERT INTO users(user_id, login, first_name, last_name)
VALUES ('example-user-id-1', 'user-1', 'User', 'Userenko')
ON CONFLICT (user_id) DO NOTHING;

INSERT INTO users(user_id, login, first_name, last_name)
VALUES ('example-user-id-2', 'user-2', 'Testuser', 'Testov')
ON CONFLICT (user_id) DO NOTHING;