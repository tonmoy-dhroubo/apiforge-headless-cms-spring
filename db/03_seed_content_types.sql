INSERT INTO content_types (id, name, plural_name, api_id, description) VALUES
    (1, 'Author', 'Authors', 'author', 'Writer profiles for editorial content'),
    (2, 'Category', 'Categories', 'category', 'Content categories for navigation'),
    (3, 'Tag', 'Tags', 'tag', 'Keyword tags for filtering'),
    (4, 'Article', 'Articles', 'article', 'Long-form editorial content'),
    (5, 'Product', 'Products', 'product', 'Catalog entries for commerce');

INSERT INTO fields (id, name, field_name, type, required, "unique", target_content_type, relation_type, content_type_id) VALUES
    (1, 'Name', 'name', 'SHORT_TEXT', TRUE, FALSE, NULL, NULL, 1),
    (2, 'Bio', 'bio', 'LONG_TEXT', FALSE, FALSE, NULL, NULL, 1),
    (3, 'Email', 'email', 'SHORT_TEXT', FALSE, TRUE, NULL, NULL, 1),
    (4, 'Avatar', 'avatar', 'MEDIA', FALSE, FALSE, NULL, NULL, 1),

    (5, 'Title', 'title', 'SHORT_TEXT', TRUE, FALSE, NULL, NULL, 2),
    (6, 'Slug', 'slug', 'SHORT_TEXT', TRUE, TRUE, NULL, NULL, 2),
    (7, 'Description', 'description', 'LONG_TEXT', FALSE, FALSE, NULL, NULL, 2),

    (8, 'Label', 'label', 'SHORT_TEXT', TRUE, TRUE, NULL, NULL, 3),
    (9, 'Color', 'color', 'SHORT_TEXT', FALSE, FALSE, NULL, NULL, 3),

    (10, 'Title', 'title', 'SHORT_TEXT', TRUE, FALSE, NULL, NULL, 4),
    (11, 'Slug', 'slug', 'SHORT_TEXT', TRUE, TRUE, NULL, NULL, 4),
    (12, 'Body', 'body', 'RICH_TEXT', FALSE, FALSE, NULL, NULL, 4),
    (13, 'Author', 'author_id', 'RELATION', TRUE, FALSE, 'author', 'MANY_TO_ONE', 4),
    (14, 'Category', 'category_id', 'RELATION', FALSE, FALSE, 'category', 'MANY_TO_ONE', 4),
    (15, 'Hero Image', 'hero_image', 'MEDIA', FALSE, FALSE, NULL, NULL, 4),
    (16, 'Published At', 'published_at', 'DATETIME', FALSE, FALSE, NULL, NULL, 4),
    (17, 'Is Published', 'is_published', 'BOOLEAN', FALSE, FALSE, NULL, NULL, 4),

    (18, 'Name', 'name', 'SHORT_TEXT', TRUE, FALSE, NULL, NULL, 5),
    (19, 'SKU', 'sku', 'SHORT_TEXT', TRUE, TRUE, NULL, NULL, 5),
    (20, 'Price', 'price', 'NUMBER', TRUE, FALSE, NULL, NULL, 5),
    (21, 'Description', 'description', 'LONG_TEXT', FALSE, FALSE, NULL, NULL, 5),
    (22, 'Category', 'category_id', 'RELATION', FALSE, FALSE, 'category', 'MANY_TO_ONE', 5),
    (23, 'Tag', 'tag_id', 'RELATION', FALSE, FALSE, 'tag', 'MANY_TO_ONE', 5),
    (24, 'Primary Image', 'primary_image', 'MEDIA', FALSE, FALSE, NULL, NULL, 5),
    (25, 'In Stock', 'in_stock', 'BOOLEAN', FALSE, FALSE, NULL, NULL, 5);
