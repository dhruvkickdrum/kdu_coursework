GRANT USAGE ON SCHEMA public TO hospital;
GRANT CREATE ON SCHEMA public TO hospital;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS shift_type (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id   UUID NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_shift_type_tenant_name UNIQUE (tenant_id, name)
    );

CREATE TABLE IF NOT EXISTS users (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id  UUID NOT NULL,
    username   VARCHAR(120) NOT NULL,
    logged_in  BOOLEAN NOT NULL DEFAULT FALSE,
    timezone   VARCHAR(64) NOT NULL DEFAULT 'UTC',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_users_tenant_username UNIQUE (tenant_id, username)
    );

CREATE TABLE IF NOT EXISTS shift (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id     UUID NOT NULL,
    shift_type_id UUID NOT NULL,
    date_start    DATE NOT NULL,
    date_end      DATE NOT NULL,
    time_start    TIME NOT NULL,
    time_end      TIME NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_shift_shift_type FOREIGN KEY (shift_type_id) REFERENCES shift_type(id),
    CONSTRAINT ck_shift_date_range CHECK (date_end >= date_start)
    );

CREATE TABLE IF NOT EXISTS shift_user (
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    shift_id  UUID NOT NULL,
    user_id   UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_shift_user_shift FOREIGN KEY (shift_id) REFERENCES shift(id),
    CONSTRAINT fk_shift_user_user  FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uq_shift_user UNIQUE (tenant_id, shift_id, user_id)
    );

CREATE INDEX IF NOT EXISTS idx_users_tenant_username ON users(tenant_id, username);
CREATE INDEX IF NOT EXISTS idx_shift_tenant_date ON shift(tenant_id, date_start);
CREATE INDEX IF NOT EXISTS idx_shift_user_tenant_shift ON shift_user(tenant_id, shift_id);
