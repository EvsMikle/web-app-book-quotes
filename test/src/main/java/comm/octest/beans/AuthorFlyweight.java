package comm.octest.beans;

import java.sql.SQLException;
import java.util.List;

public interface AuthorFlyweight {

	void save(AuthorFlyweight author) throws SQLException;

	List<AuthorFlyweight> fetchAuthors() throws SQLException;

	String getAuthor_name();

	void setAuthor_name(String author_name);

	int getId_book();

	void setId_book(int id_book);

	int getId_author();

	void setId_author(int id_author);

	int getId_type();

	void setId_type(int id_type);

	void updateAuthor(AuthorFlyweight author) throws SQLException;
	void removeAuthor(AuthorFlyweight author) throws SQLException ;
	public int getId_quote() ;

	public void setId_quote(int id_quote) ;
	void updateId(AuthorFlyweight author) throws SQLException;


}
