INSERT INTO categories (name)
VALUES ('Sci-Fi');


UPDATE movies
SET category_id = (SELECT id FROM categories WHERE name = 'Sci-Fi')
WHERE id = 1;