-- Create Application for managing library work.
__________________________________________________________________________________________________
-- All users should be able:
-- 1.	Get information about all books:
 SELECT * FROM books;
-- 2.	Check if needed book is available
SELECT * FROM books WHERE title = 'bookname' AND amount > 0;
-- 3.	Find books by author (main author, co-author)
-- By author:
SELECT * FROM books WHERE main_author_id IN
                          (SELECT id FROM authors WHERE firstname LIKE 'Dan' AND lastname LIKE 'Brown');
--  By co-author:
SELECT * FROM books WHERE id IN (SELECT book_id FROM books_authors WHERE author_id = 2);
-- 4.	Find book by title
SELECT * FROM books WHERE book_title LIKE 'The Da Vinci Code';
-- 5.	Get the most popular and the most unpopular books in selected period
SELECT book_id, COUNT(book_id) AS counted
FROM loggers
WHERE start_date > '2022-01-29' AND start_date < '2022-02-03'
GROUP BY book_id
ORDER BY book_id;
-- 6.	*Registration functionality
-- 7.	*Log in
__________________________________________________________________________________________________
-- Reader should be able
-- 1.	Request needed book
INSERT INTO logger(BOOK_ID, USER_ID, status) VALUES (1, 1, 'requested');
-- 2.	Return book
UPDATE logger SET return_date = current_timestamp, status = 'returned' WHERE user_id = 5 AND book_id = 1;
UPDATE books SET available_amount = available_amount + 1 WHERE id = 1;
-- 3.	Get his/her statistics (how many and how long books were been read, reading now)
SELECT * FROM logger WHERE user_id LIKE 1;
-- 4.	Get the most popular and the most unpopular books in selected period
SELECT book_id, COUNT(book_id) AS counted
FROM loggers
WHERE start_date > '2022-01-29' AND start_date < '2022-02-03'
GROUP BY book_id
ORDER BY book_id;
-- 5.	*Get notification on email
__________________________________________________________________________________________________
-- Manager should be able
-- 1.	Register book with copies
INSERT INTO books (book_title, available_amount, main_author_id) VALUES ('Title', 4,
            (SELECT id FROM authors WHERE firstname LIKE 'Author firstname' AND lastname LIKE 'Author lastname'));
-- 2.	Update book’ information
UPDATE books SET book_title = 'New Title' WHERE book_title LIKE 'Old title';
-- 3.	Delete One copy/Book with all copies
-- Delete one copy of book
UPDATE books set available_amount = available_amount - 1 WHERE  book_title LIKE 'Title';
-- Delete book (all copies)
DELETE FROM books WHERE book_title LIKE 'Title';
-- 4.	Give book to Reader
UPDATE books SET available_amount = available_amount - 1 WHERE id = 1;
UPDATE loggers SET start_date = current_timestamp WHERE user_id = 1 AND book_id = 1;
-- 5.	Set title of book and display count of this Book’ copies with information about them (available/unavailable in Library)
UPDATE books SET book_title = 'Title' WHERE id = 1;
SELECT available_amount FROM books WHERE id = 1;
-- 6.	Get statistics by Reader (books which this user has read, is reading, how long he is our client)
-- has read
SELECT * FROM loggers WHERE user_id = 1 AND return_date IS NOT NULL;
-- is reading
SELECT * FROM loggers WHERE user_id = 1 AND return_date IS NULL;
--how long
SELECT current_timestamp - registration_date FROM users WHERE id = 1;
-- 7.	Get statistics by Book (general, by copies, average time of reading)
SELECT * FROM books WHERE id = 1;
SELECT author_id FROM books_authors WHERE book_id = 1;
SELECT available_amount FROM books WHERE id = 1;
SELECT AVG(return_date - start_date) FROM loggers WHERE book_id = 1;
-- 8.	Get statistics by Readers (average age, time of working with Library, average number of requests to Library in selected period)
SELECT AVG(age) FROM users;
SELECT AVG(current_timestamp - registration_date) FROM users;
SELECT COUNT(*) FROM loggers WHERE start_date > '2022-01-29' AND start_date < '2022-02-03';
SELECT COUNT(*) FROM users;
-- 9.	Get list of users who has not returned book in time with detailed information about them
SELECT * FROM users WHERE id IN
            (SELECT user_id FROM loggers WHERE deadline_date < return_date);
-- 10.	How many books were giving in selected period?
SELECT COUNT(*) FROM loggers WHERE start_date > '2022-01-29' AND start_date < '2022-02-03';
-- 11.	*Send notification to one reader, all reader
__________________________________________________________________________________________________
