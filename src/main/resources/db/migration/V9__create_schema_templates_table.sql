CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE schema_templates (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    category VARCHAR(30) NOT NULL,
    schema_definition JSONB NOT NULL,
    is_system BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'User Authentication',
    'user-auth',
    'Basic user authentication schema',
    'AUTH',
    '{
      "fields": [
        { "name": "id", "type": "UUID", "required": true },
        { "name": "email", "type": "STRING", "required": true, "unique": true },
        { "name": "password", "type": "STRING", "required": true },
        { "name": "role", "type": "ENUM", "values": ["USER", "ADMIN"] },
        { "name": "createdAt", "type": "TIMESTAMP" }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'User Profile',
    'user-profile',
    'User profile information',
    'USER',
    '{
      "fields": [
        { "name": "id", "type": "UUID", "required": true },
        { "name": "firstName", "type": "STRING" },
        { "name": "lastName", "type": "STRING" },
        { "name": "phone", "type": "STRING" },
        { "name": "address", "type": "STRING" }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'Product',
    'product',
    'Ecommerce product schema',
    'ECOMMERCE',
    '{
      "fields": [
        { "name": "id", "type": "UUID", "required": true },
        { "name": "name", "type": "STRING", "required": true },
        { "name": "price", "type": "DECIMAL", "required": true },
        { "name": "stock", "type": "INT" }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'Order',
    'order',
    'Order schema',
    'ECOMMERCE',
    '{
      "fields": [
        { "name": "id", "type": "UUID" },
        { "name": "userId", "type": "UUID" },
        { "name": "totalAmount", "type": "DECIMAL" },
        { "name": "status", "type": "ENUM", "values": ["CREATED", "PAID", "SHIPPED"] }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'Payment',
    'payment',
    'Payment schema',
    'ECOMMERCE',
    '{
      "fields": [
        { "name": "id", "type": "UUID" },
        { "name": "orderId", "type": "UUID" },
        { "name": "amount", "type": "DECIMAL" },
        { "name": "method", "type": "ENUM", "values": ["CARD", "UPI", "COD"] }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'Blog Post',
    'blog-post',
    'Blog post schema',
    'CMS',
    '{
      "fields": [
        { "name": "id", "type": "UUID" },
        { "name": "title", "type": "STRING" },
        { "name": "content", "type": "TEXT" },
        { "name": "authorId", "type": "UUID" }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'Comment',
    'comment',
    'Comment schema',
    'CMS',
    '{
      "fields": [
        { "name": "id", "type": "UUID" },
        { "name": "postId", "type": "UUID" },
        { "name": "message", "type": "TEXT" }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'Notification',
    'notification',
    'System notifications',
    'SYSTEM',
    '{
      "fields": [
        { "name": "id", "type": "UUID" },
        { "name": "userId", "type": "UUID" },
        { "name": "message", "type": "STRING" },
        { "name": "read", "type": "BOOLEAN" }
      ]
    }',
    true,
    now(),
    now()
);
INSERT INTO schema_templates VALUES
(
    gen_random_uuid(),
    'Audit Log',
    'audit-log',
    'System audit logs',
    'SYSTEM',
    '{
      "fields": [
        { "name": "id", "type": "UUID" },
        { "name": "entity", "type": "STRING" },
        { "name": "action", "type": "STRING" },
        { "name": "timestamp", "type": "TIMESTAMP" }
      ]
    }',
    true,
    now(),
    now()
);

