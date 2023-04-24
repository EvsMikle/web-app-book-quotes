package comm.octest.beans;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookFactory {

	private Map<String, I_book> books = new HashMap<>();

	public I_book addBook(String author_name, String type, String book_name) throws SQLException {
		// CREATE THE KEY
		String key = author_name + type + book_name;

		fetchBooks();
		I_book book = (Book) books.get(key);
		// IF IT DOESN'T EXIST, CREATE A NEW ONE IN THE MAP
		if (book == null) {
			book = new Book(book_name, type, author_name);
			books.put(key, book);
			book.save(book);

		}
		return book;
	}

	public void fetchBooks() throws SQLException {
		I_book book = new Book();
		List<I_book> booksList = book.fetchBooks();

		for (I_book b : booksList) {
			String book_name = b.getName_book();
			String type_name = b.getType();
			String author_name = b.getAuthor();

			String key = author_name + type_name + book_name;

			books.put(key, book);
		}

	}

}
