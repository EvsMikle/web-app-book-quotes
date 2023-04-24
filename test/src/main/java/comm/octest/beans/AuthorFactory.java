package comm.octest.beans;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorFactory {
	private static Map<String, I_author> authors = new HashMap<>();

	public I_author addAuthor(String author_name) throws SQLException {
		// CREATE THE KEY
		String key = author_name;
		System.out.print(key);
		fetchAuthors();
		I_author author = (I_author) authors.get(key);

		// IF IT DOESN'T EXIST, CREATE A NEW ONE IN THE MAP
		if (author == null) {
			author = new Author(author_name);
			authors.put(key, author);
			author.save(author);
		}

		return author;
	}

	public void fetchAuthors() throws SQLException {
		I_author author = new Author();
		List<I_author> authorsList = author.fetchAuthors();
		for (I_author a : authorsList) {
			String key = a.getAuthor_name();
			authors.put(key, a);

		}
	}

}
