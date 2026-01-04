INSERT INTO ct_author (id, name, bio, email, avatar) VALUES
    (1, 'Jane Doe', 'Tech journalist focusing on developer tools.', 'jane.doe@apiforge.com', 2),
    (2, 'John Smith', 'Product writer covering hardware and design.', 'john.smith@apiforge.com', 3),
    (3, 'Alex Lee', 'Editor for long-form stories and interviews.', 'alex.lee@apiforge.com', 4),
    (4, 'Priya Nair', 'Writes about cloud infrastructure and APIs.', 'priya.nair@apiforge.com', 1),
    (5, 'Miguel Santos', 'Focuses on data engineering and analytics.', 'miguel.santos@apiforge.com', 10),
    (6, 'Sofia Keller', 'Lifestyle and productivity writer.', 'sofia.keller@apiforge.com', 2),
    (7, 'Ethan Brooks', 'Covers security and compliance.', 'ethan.brooks@apiforge.com', 3),
    (8, 'Hana Park', 'Writes about UX and product strategy.', 'hana.park@apiforge.com', 4),
    (9, 'Oliver Grant', 'Hardware reviews and field tests.', 'oliver.grant@apiforge.com', 1),
    (10, 'Lina Patel', 'Editor of the weekly newsletter.', 'lina.patel@apiforge.com', 10);

INSERT INTO ct_category (id, title, slug, description) VALUES
    (1, 'Engineering', 'engineering', 'Backend and platform engineering'),
    (2, 'Product', 'product', 'Product design and management'),
    (3, 'Tutorials', 'tutorials', 'Step-by-step guides'),
    (4, 'News', 'news', 'Company and industry updates'),
    (5, 'Reviews', 'reviews', 'Hands-on product reviews'),
    (6, 'Guides', 'guides', 'Practical workflow guides'),
    (7, 'Design', 'design', 'Visual and interaction design'),
    (8, 'Security', 'security', 'Security and compliance'),
    (9, 'Data', 'data', 'Analytics and data engineering'),
    (10, 'Commerce', 'commerce', 'Catalog and ecommerce updates');

INSERT INTO ct_tag (id, label, color) VALUES
    (1, 'api', 'blue'),
    (2, 'cloud', 'teal'),
    (3, 'ux', 'orange'),
    (4, 'performance', 'red'),
    (5, 'security', 'purple'),
    (6, 'launch', 'green'),
    (7, 'hardware', 'gray'),
    (8, 'tutorial', 'yellow'),
    (9, 'data', 'navy'),
    (10, 'product', 'magenta');

INSERT INTO ct_article (id, title, slug, body, author_id, category_id, hero_image, published_at, is_published) VALUES
    (1, 'Designing an API-first CMS', 'designing-api-first-cms', '<p>Principles for API-first architecture.</p>', 1, 1, 1, CURRENT_TIMESTAMP - INTERVAL '30 days', TRUE),
    (2, 'Shipping content types fast', 'shipping-content-types-fast', '<p>Tips for rapid schema iteration.</p>', 3, 3, 10, CURRENT_TIMESTAMP - INTERVAL '25 days', TRUE),
    (3, 'Why editorial workflows matter', 'why-editorial-workflows-matter', '<p>Aligning teams with clear processes.</p>', 8, 2, 1, CURRENT_TIMESTAMP - INTERVAL '21 days', TRUE),
    (4, 'Scaling media pipelines', 'scaling-media-pipelines', '<p>Patterns for media-heavy content.</p>', 4, 1, 10, CURRENT_TIMESTAMP - INTERVAL '18 days', TRUE),
    (5, 'Security checklist for CMS teams', 'security-checklist-cms', '<p>Secure defaults and audits.</p>', 7, 8, 1, CURRENT_TIMESTAMP - INTERVAL '15 days', TRUE),
    (6, 'Editorial design systems', 'editorial-design-systems', '<p>Building reusable content blocks.</p>', 8, 7, 10, CURRENT_TIMESTAMP - INTERVAL '12 days', TRUE),
    (7, 'Content ops playbook', 'content-ops-playbook', '<p>Operational excellence for content teams.</p>', 10, 6, 1, CURRENT_TIMESTAMP - INTERVAL '9 days', TRUE),
    (8, 'Data-driven publishing', 'data-driven-publishing', '<p>Measuring engagement and iteration.</p>', 5, 9, 10, CURRENT_TIMESTAMP - INTERVAL '6 days', TRUE),
    (9, 'Hardware review: Studio camera', 'hardware-review-studio-camera', '<p>Testing a creator-focused camera.</p>', 9, 5, 5, CURRENT_TIMESTAMP - INTERVAL '3 days', TRUE),
    (10, 'Weekly release notes', 'weekly-release-notes', '<p>Highlights from this week.</p>', 10, 4, 10, CURRENT_TIMESTAMP - INTERVAL '1 day', TRUE);

INSERT INTO ct_product (id, name, sku, price, description, category_id, tag_id, primary_image, in_stock) VALUES
    (1, 'Vintage Camera', 'CAM-001', 249.99, 'Classic 35mm camera with manual controls.', 10, 7, 5, TRUE),
    (2, 'Explorer Backpack', 'BAG-014', 129.50, 'Durable travel backpack with modular storage.', 10, 10, 6, TRUE),
    (3, 'Heritage Watch', 'WAT-009', 349.00, 'Analog watch with leather strap.', 10, 10, 7, TRUE),
    (4, 'Studio Lamp', 'LMP-120', 89.00, 'Adjustable desk lamp for creators.', 10, 3, 8, TRUE),
    (5, 'Field Notebook', 'NBK-450', 24.00, 'Leather-bound notebook for sketches.', 10, 3, 9, TRUE),
    (6, 'Wireless Mic', 'MIC-332', 199.00, 'Compact wireless mic system.', 10, 7, 1, TRUE),
    (7, 'Desk Organizer', 'ORG-210', 39.00, 'Minimal organizer for your workspace.', 10, 3, 8, TRUE),
    (8, 'Creator Tripod', 'TRI-118', 159.00, 'Sturdy tripod for studio setups.', 10, 7, 5, TRUE),
    (9, 'Travel Bottle', 'BOT-777', 29.50, 'Insulated bottle for long days.', 10, 10, 6, TRUE),
    (10, 'Compact Router', 'RTR-888', 219.00, 'High-performance router for remote teams.', 10, 2, 1, TRUE);
