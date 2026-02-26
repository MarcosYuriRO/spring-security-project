CREATE TABLE roles (
    role_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles (
    user_id UUID NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

CREATE TABLE tweets (
    tweet_id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    content VARCHAR(280) NOT NULL,
    creation_timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tweets_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);
