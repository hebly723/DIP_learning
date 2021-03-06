package com.graduate.web;

import com.graduate.entity.Book;
import com.graduate.entity.Image;
import com.graduate.service.BookService;
import com.graduate.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/image")
public class ImageController {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private BookService bookService;
  @Autowired
  private ImageService imageService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  private String list(Model model) {
//    List<Book> list = bookService.getList(0, 1000);
    List<Image> list = imageService.getDump();
    model.addAttribute("list", list);
    return "imagelist";// WEB-INF/jsp/"list".jsp
  }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private String search(Model model) {
        return "search";
    }

  @RequestMapping(value = "/detail/{bookId}", method = RequestMethod.GET)
  private String detail(@PathVariable("bookId") Long bookId, Model model) {
    Book book = bookService.getById(bookId);
    model.addAttribute("book", book);
    return "detail";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
  @ResponseBody
  private String add(Book book) {
    Book hasBook = bookService.getById(book.getBookId());
    int i = -2;
    if (hasBook == null) {
      i = bookService.addBook(book);
    }
    return i > 0 ? "success" : "error";
  }

  @RequestMapping(value = "/del/{bookId}", method = RequestMethod.GET)
  @ResponseBody
  private String deleteBookById(@PathVariable("bookId") Long id) {
    int i = bookService.deleteBookById(id);
    return i > 0 ? "success" : "error";
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  private String test(Model model) {
    return "testFile";
  }
}
