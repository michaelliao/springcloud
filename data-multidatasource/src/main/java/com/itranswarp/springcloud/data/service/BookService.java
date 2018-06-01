package com.itranswarp.springcloud.data.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.springcloud.data.domain.Book;
import com.itranswarp.springcloud.data.request.BookReq;

/**
 * Book service which uses the second data source.
 * 
 * @author Michael Liao
 */
@RestController
public class BookService {

	@Autowired
	@Qualifier("secondJdbcTemplate")
	JdbcTemplate secondJdbcTemplate;

	@PostConstruct
	public void init() {
		secondJdbcTemplate.execute("DROP TABLE IF EXISTS book");
		secondJdbcTemplate.execute("CREATE TABLE book (" //
				+ "id VARCHAR(50) NOT NULL," //
				+ "name VARCHAR(50) NOT NULL," //
				+ "isbn VARCHAR(50) NOT NULL," //
				+ "createdAt BIGINT NOT NULL," //
				+ "updatedAt BIGINT NOT NULL," //
				+ "PRIMARY KEY (id))");
		for (int i = 0; i < 25; i++) {
			BookReq req = new BookReq();
			req.setName("Java-" + randomString());
			req.setIsbn("ISBN-" + randomString().hashCode() % 2017);
			createBook(req);
		}
	}

	@PostMapping("/api/books")
	public Book createBook(@RequestBody BookReq req) {
		String id = randomString();
		Long now = System.currentTimeMillis();
		secondJdbcTemplate.update("INSERT INTO book (id, name, isbn, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)", // SQL
				id, req.getName(), req.getIsbn(), now, now);
		return getBook(id);
	}

	@GetMapping("/api/books")
	public List<Book> getBooks() {
		return secondJdbcTemplate.query("SELECT * FROM book ORDER BY createdAt",
				new BeanPropertyRowMapper<>(Book.class));
	}

	@GetMapping("/api/books/{id}")
	public Book getBook(@PathVariable("id") String id) {
		return secondJdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?",
				new BeanPropertyRowMapper<>(Book.class), id);
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

}
