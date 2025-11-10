CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE movies ADD COLUMN category_id BIGINT;

ALTER TABLE movies
ADD CONSTRAINT fk_movie_category
FOREIGN KEY (category_id) REFERENCES categories(id);