package comm.octest.beans;

import java.sql.SQLException;
import java.util.List;

import comm.octest.dao.BookAuthorDAO;
import comm.octest.dao.BookAuthor.AuthorDAO;

public class Author implements I_author {

	private String author_name;
	private int id_book;
	private int id_author;
	private int id_type;
	private BookAuthorDAO<I_author> authorDAO;

	public Author(String author_name) {
		this.author_name = author_name;
		authorDAO= new AuthorDAO() ; 
	}

	public Author() {
		authorDAO= new AuthorDAO() ; 
	}

	public void save(I_author author) throws SQLException {
	
		authorDAO.insert(author);
		System.out.println("Author bien saisie ! " + author_name);
	}

	public List<I_author> fetchAuthors() throws SQLException {
		
		List<I_author> authorsList = authorDAO.fetch();
		return authorsList;
	}

	

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public int getId_author() {
		return id_author;
	}

	public void setId_author(int id_author) {
		this.id_author = id_author;
	}

	public int getId_type() {
		return id_type;
	}

	public void setId_type(int id_type) {
		this.id_type = id_type;
	}



}
