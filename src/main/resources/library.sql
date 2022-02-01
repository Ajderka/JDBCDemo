CREATE TABLE users (
    id SERIAL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    age INT NOT NULL,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT UQ_users_email UNIQUE (email),
    CONSTRAINT FK_users_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE roles (
    id SERIAL,
    role_name VARCHAR(255) NOT NULL,
    CONSTRAINT PK_roles PRIMARY KEY (id)
);

CREATE TABLE authors (
    id SERIAL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    CONSTRAINT PK_authors PRIMARY KEY (id)
);

CREATE TABLE books (
    id SERIAL,
    title VARCHAR(255) NOT NULL,
    available_amount INT DEFAULT 1,
    main_author_id INT NOT NULL,
    CONSTRAINT PK_books PRIMARY KEY (id),
    CONSTRAINT FK_books_authors FOREIGN KEY (main_author_id) REFERENCES authors(id)
);

CREATE TABLE loggers (
    id SERIAL,
    book_id INT NOT NULL,
    user_id INT NOT NULL,
    date_of_start DATE DEFAULT now(),
    date_of_return DATE DEFAULT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT PK_loggers PRIMARY KEY (id),
    CONSTRAINT FK_loggers_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_loggers_books FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE co_author (
    books_id INT,
    authors_id INT,
    CONSTRAINT PK_co_author PRIMARY KEY (books_id, authors_id),
    CONSTRAINT FK_co_author_books FOREIGN KEY (books_id) REFERENCES books(id),
    CONSTRAINT FK_co_author_authors FOREIGN KEY (authors_id) REFERENCES authors(id)
);

