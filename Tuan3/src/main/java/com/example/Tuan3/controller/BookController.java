package com.example.Tuan3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Tuan3.model.Book;
import com.example.Tuan3.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public String listBooks(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "books";
	}

	@GetMapping("/add")
	public String addBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "add-book";
	}

	@PostMapping("/add")
	public String addBook(@ModelAttribute Book book) {
		bookService.addBook(book);
		return "redirect:/books";
	}

	@GetMapping("/edit/{id}")
	public String editBookForm(@PathVariable Long id, Model model) {
		return bookService.getBookById(id)
				.map(book -> {
					model.addAttribute("book", book);
					return "edit-book";
				})
				.orElse("redirect:/books");
	}

	@PostMapping("/edit")
	public String updateBook(@ModelAttribute Book book) {
		bookService.updateBook(book);
		return "redirect:/books";
	}

	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return "redirect:/books";
	}
}
