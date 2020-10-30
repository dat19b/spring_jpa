INSERT INTO category (description) VALUES ('American');
INSERT INTO category (description) VALUES ('Italian');
INSERT INTO category (description) VALUES ('Mexican');
INSERT INTO category (description) VALUES ('Fast Food');
-- INSERT INTO unit_of_measure (description) VALUES ('Teaspoon');
-- INSERT INTO unit_of_measure (description) VALUES ('Tablespoon');
-- INSERT INTO unit_of_measure (description) VALUES ('Cup');
-- INSERT INTO unit_of_measure (description) VALUES ('Pinch');
-- INSERT INTO unit_of_measure (description) VALUES ('Ounce');
INSERT INTO recipe (description, prep_Time, cook_Time, servings, source, url, directions, xxx)
    VALUES ('Porrige', 60, 20, 4, 'Margarat', 'https://www.bbcgoodfood.com/recipes/perfect-porridge', 'Start by cooking milk', 'XXX');
INSERT INTO recipe (description, prep_Time, cook_Time, servings, source, url, directions, xxx)
    VALUES ('Bread', 10, 20, 10, 'Cassie', 'https://www.bbcgoodfood.com/recipes/seed-grain-cottage-loaf', 'Start by cooking milk - always', 'YYY');

INSERT INTO notes (description, recipe_id) VALUES ('This should be done by all people', 1);
UPDATE recipe SET notes_id = 1 WHERE id = 1;
INSERT INTO notes (description, recipe_id) VALUES ('Good for breakfast', 2);
UPDATE recipe SET notes_id = 2 WHERE id = 2;