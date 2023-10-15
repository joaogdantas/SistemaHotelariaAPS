CREATE TABLE IF NOT EXISTS itens (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    min_stock_level INT NOT NULL
);