INSERT INTO roles (id, name, description) VALUES
    (1, 'SUPER_ADMIN', 'Full system access'),
    (2, 'ADMIN', 'Administrative access'),
    (3, 'MODERATOR', 'Content moderation access'),
    (4, 'REGISTERED', 'Default registered user');

INSERT INTO permissions (id, name, description) VALUES
    (1, 'MANAGE_USERS', 'Create and manage users'),
    (2, 'MANAGE_CONTENT_TYPES', 'Create and manage content types'),
    (3, 'MANAGE_CONTENT', 'Create and manage content entries'),
    (4, 'VIEW_CONTENT', 'Read content entries'),
    (5, 'MANAGE_MEDIA', 'Upload and manage media'),
    (6, 'MANAGE_PERMISSIONS', 'Configure permissions');

INSERT INTO role_permissions (role_id, permission_id) VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
    (2, 2), (2, 3), (2, 4), (2, 5),
    (3, 3), (3, 4), (3, 5),
    (4, 4);

INSERT INTO users (id, username, email, password, firstname, lastname, enabled) VALUES
    (1, 'super_admin', 'super_admin@apiforge.com', crypt('password123', gen_salt('bf')), 'Super', 'Admin', TRUE),
    (2, 'admin', 'admin@apiforge.com', crypt('password123', gen_salt('bf')), 'System', 'Admin', TRUE),
    (3, 'moderator', 'moderator@apiforge.com', crypt('password123', gen_salt('bf')), 'Content', 'Moderator', TRUE),
    (4, 'jane', 'jane.doe@apiforge.com', crypt('password123', gen_salt('bf')), 'Jane', 'Doe', TRUE),
    (5, 'john', 'john.smith@apiforge.com', crypt('password123', gen_salt('bf')), 'John', 'Smith', TRUE),
    (6, 'alex', 'alex.lee@apiforge.com', crypt('password123', gen_salt('bf')), 'Alex', 'Lee', TRUE);

INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1), (1, 2),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 4),
    (6, 4);
