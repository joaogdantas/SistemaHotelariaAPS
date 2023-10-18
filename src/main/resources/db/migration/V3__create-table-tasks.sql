CREATE TABLE IF NOT EXISTS tasks (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    task_type VARCHAR(255) NOT NULL,
    done BOOLEAN,
    user_id UUID,

    FOREIGN KEY (user_id) REFERENCES users (id)
);