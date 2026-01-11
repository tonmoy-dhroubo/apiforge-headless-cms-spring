INSERT INTO content_permissions (id, content_type_api_id, action) VALUES
    (1, 'author', 'CREATE'),
    (2, 'author', 'READ'),
    (3, 'author', 'UPDATE'),
    (4, 'author', 'DELETE'),
    (5, 'category', 'CREATE'),
    (6, 'category', 'READ'),
    (7, 'category', 'UPDATE'),
    (8, 'category', 'DELETE'),
    (9, 'tag', 'CREATE'),
    (10, 'tag', 'READ'),
    (11, 'tag', 'UPDATE'),
    (12, 'tag', 'DELETE'),
    (13, 'article', 'CREATE'),
    (14, 'article', 'READ'),
    (15, 'article', 'UPDATE'),
    (16, 'article', 'DELETE'),
    (17, 'product', 'CREATE'),
    (18, 'product', 'READ'),
    (19, 'product', 'UPDATE'),
    (20, 'product', 'DELETE');

INSERT INTO content_permission_roles (permission_id, role_name) VALUES
    (1, 'SUPER_ADMIN'), (1, 'ADMIN'),
    (2, 'SUPER_ADMIN'), (2, 'ADMIN'), (2, 'MODERATOR'), (2, 'REGISTERED'),
    (3, 'SUPER_ADMIN'), (3, 'ADMIN'), (3, 'MODERATOR'),
    (4, 'SUPER_ADMIN'), (4, 'ADMIN'),

    (5, 'SUPER_ADMIN'), (5, 'ADMIN'),
    (6, 'SUPER_ADMIN'), (6, 'ADMIN'), (6, 'MODERATOR'), (6, 'REGISTERED'),
    (7, 'SUPER_ADMIN'), (7, 'ADMIN'), (7, 'MODERATOR'),
    (8, 'SUPER_ADMIN'), (8, 'ADMIN'),

    (9, 'SUPER_ADMIN'), (9, 'ADMIN'),
    (10, 'SUPER_ADMIN'), (10, 'ADMIN'), (10, 'MODERATOR'), (10, 'REGISTERED'),
    (11, 'SUPER_ADMIN'), (11, 'ADMIN'), (11, 'MODERATOR'),
    (12, 'SUPER_ADMIN'), (12, 'ADMIN'),

    (13, 'SUPER_ADMIN'), (13, 'ADMIN'), (13, 'MODERATOR'),
    (14, 'SUPER_ADMIN'), (14, 'ADMIN'), (14, 'MODERATOR'), (14, 'REGISTERED'),
    (15, 'SUPER_ADMIN'), (15, 'ADMIN'), (15, 'MODERATOR'),
    (16, 'SUPER_ADMIN'), (16, 'ADMIN'),

    (17, 'SUPER_ADMIN'), (17, 'ADMIN'),
    (18, 'SUPER_ADMIN'), (18, 'ADMIN'), (18, 'MODERATOR'), (18, 'REGISTERED'),
    (19, 'SUPER_ADMIN'), (19, 'ADMIN'), (19, 'MODERATOR'),
    (20, 'SUPER_ADMIN'), (20, 'ADMIN');

INSERT INTO api_permissions (id, content_type_api_id, endpoint, method) VALUES
    (1, 'author', '/api/content/author', 'GET'),
    (2, 'author', '/api/content/author', 'POST'),
    (3, 'author', '/api/content/author/{id}', 'GET'),
    (4, 'author', '/api/content/author/{id}', 'PUT'),
    (5, 'author', '/api/content/author/{id}', 'DELETE'),

    (6, 'category', '/api/content/category', 'GET'),
    (7, 'category', '/api/content/category', 'POST'),
    (8, 'category', '/api/content/category/{id}', 'GET'),
    (9, 'category', '/api/content/category/{id}', 'PUT'),
    (10, 'category', '/api/content/category/{id}', 'DELETE'),

    (11, 'tag', '/api/content/tag', 'GET'),
    (12, 'tag', '/api/content/tag', 'POST'),
    (13, 'tag', '/api/content/tag/{id}', 'GET'),
    (14, 'tag', '/api/content/tag/{id}', 'PUT'),
    (15, 'tag', '/api/content/tag/{id}', 'DELETE'),

    (16, 'article', '/api/content/article', 'GET'),
    (17, 'article', '/api/content/article', 'POST'),
    (18, 'article', '/api/content/article/{id}', 'GET'),
    (19, 'article', '/api/content/article/{id}', 'PUT'),
    (20, 'article', '/api/content/article/{id}', 'DELETE'),

    (21, 'product', '/api/content/product', 'GET'),
    (22, 'product', '/api/content/product', 'POST'),
    (23, 'product', '/api/content/product/{id}', 'GET'),
    (24, 'product', '/api/content/product/{id}', 'PUT'),
    (25, 'product', '/api/content/product/{id}', 'DELETE');

INSERT INTO api_permission_roles (permission_id, role_name) VALUES
    (1, 'SUPER_ADMIN'), (1, 'ADMIN'), (1, 'MODERATOR'), (1, 'REGISTERED'),
    (2, 'SUPER_ADMIN'), (2, 'ADMIN'),
    (3, 'SUPER_ADMIN'), (3, 'ADMIN'), (3, 'MODERATOR'), (3, 'REGISTERED'),
    (4, 'SUPER_ADMIN'), (4, 'ADMIN'), (4, 'MODERATOR'),
    (5, 'SUPER_ADMIN'), (5, 'ADMIN'),

    (6, 'SUPER_ADMIN'), (6, 'ADMIN'), (6, 'MODERATOR'), (6, 'REGISTERED'),
    (7, 'SUPER_ADMIN'), (7, 'ADMIN'),
    (8, 'SUPER_ADMIN'), (8, 'ADMIN'), (8, 'MODERATOR'), (8, 'REGISTERED'),
    (9, 'SUPER_ADMIN'), (9, 'ADMIN'), (9, 'MODERATOR'),
    (10, 'SUPER_ADMIN'), (10, 'ADMIN'),

    (11, 'SUPER_ADMIN'), (11, 'ADMIN'), (11, 'MODERATOR'), (11, 'REGISTERED'),
    (12, 'SUPER_ADMIN'), (12, 'ADMIN'),
    (13, 'SUPER_ADMIN'), (13, 'ADMIN'), (13, 'MODERATOR'), (13, 'REGISTERED'),
    (14, 'SUPER_ADMIN'), (14, 'ADMIN'), (14, 'MODERATOR'),
    (15, 'SUPER_ADMIN'), (15, 'ADMIN'),

    (16, 'SUPER_ADMIN'), (16, 'ADMIN'), (16, 'MODERATOR'), (16, 'REGISTERED'), (16, 'PUBLIC'),
    (17, 'SUPER_ADMIN'), (17, 'ADMIN'), (17, 'MODERATOR'),
    (18, 'SUPER_ADMIN'), (18, 'ADMIN'), (18, 'MODERATOR'), (18, 'REGISTERED'), (18, 'PUBLIC'),
    (19, 'SUPER_ADMIN'), (19, 'ADMIN'), (19, 'MODERATOR'),
    (20, 'SUPER_ADMIN'), (20, 'ADMIN'),

    (21, 'SUPER_ADMIN'), (21, 'ADMIN'), (21, 'MODERATOR'), (21, 'REGISTERED'),
    (22, 'SUPER_ADMIN'), (22, 'ADMIN'),
    (23, 'SUPER_ADMIN'), (23, 'ADMIN'), (23, 'MODERATOR'), (23, 'REGISTERED'),
    (24, 'SUPER_ADMIN'), (24, 'ADMIN'), (24, 'MODERATOR'),
    (25, 'SUPER_ADMIN'), (25, 'ADMIN');
