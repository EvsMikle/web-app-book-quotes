package comm.octest.beans;

import java.sql.SQLException;
import java.util.List;

public interface I_author {

	void save(I_author author) throws SQLException;

	List<I_author> fetchAuthors() throws SQLException;

	String getAuthor_name();

	void setAuthor_name(String author_name);

	int getId_book();

	void setId_book(int id_book);

	int getId_author();

	void setId_author(int id_author);

	int getId_type();

	void setId_type(int id_type);

}
