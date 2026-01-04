CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS content_permission_roles CASCADE;
DROP TABLE IF EXISTS api_permission_roles CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS role_permissions CASCADE;
DROP TABLE IF EXISTS fields CASCADE;
DROP TABLE IF EXISTS content_types CASCADE;
DROP TABLE IF EXISTS content_permissions CASCADE;
DROP TABLE IF EXISTS api_permissions CASCADE;
DROP TABLE IF EXISTS media CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

DROP TABLE IF EXISTS ct_author CASCADE;
DROP TABLE IF EXISTS ct_category CASCADE;
DROP TABLE IF EXISTS ct_tag CASCADE;
DROP TABLE IF EXISTS ct_article CASCADE;
DROP TABLE IF EXISTS ct_product CASCADE;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id BIGINT NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE content_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    plural_name VARCHAR(255) UNIQUE NOT NULL,
    api_id VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fields (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    field_name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    required BOOLEAN,
    "unique" BOOLEAN,
    target_content_type VARCHAR(255),
    relation_type VARCHAR(255),
    content_type_id BIGINT REFERENCES content_types(id) ON DELETE CASCADE
);

CREATE TABLE media (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    alternative_text VARCHAR(255),
    caption TEXT,
    width INTEGER,
    height INTEGER,
    hash VARCHAR(255) NOT NULL,
    ext VARCHAR(255),
    mime VARCHAR(255),
    size NUMERIC,
    url TEXT,
    provider VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE api_permissions (
    id BIGSERIAL PRIMARY KEY,
    content_type_api_id VARCHAR(255) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    method VARCHAR(16) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE api_permission_roles (
    permission_id BIGINT NOT NULL REFERENCES api_permissions(id) ON DELETE CASCADE,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (permission_id, role_name)
);

CREATE TABLE content_permissions (
    id BIGSERIAL PRIMARY KEY,
    content_type_api_id VARCHAR(255) NOT NULL,
    action VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE content_permission_roles (
    permission_id BIGINT NOT NULL REFERENCES content_permissions(id) ON DELETE CASCADE,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (permission_id, role_name)
);

CREATE TABLE ct_author (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(255) NOT NULL,
    bio TEXT,
    email VARCHAR(255) UNIQUE,
    avatar BIGINT
);

CREATE TABLE ct_category (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE ct_tag (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    label VARCHAR(255) UNIQUE NOT NULL,
    color VARCHAR(255)
);

CREATE TABLE ct_article (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    body TEXT,
    author_id BIGINT,
    category_id BIGINT,
    hero_image BIGINT,
    published_at TIMESTAMP,
    is_published BOOLEAN
);

CREATE TABLE ct_product (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(255) NOT NULL,
    sku VARCHAR(255) UNIQUE NOT NULL,
    price NUMERIC NOT NULL,
    description TEXT,
    category_id BIGINT,
    tag_id BIGINT,
    primary_image BIGINT,
    in_stock BOOLEAN
);
