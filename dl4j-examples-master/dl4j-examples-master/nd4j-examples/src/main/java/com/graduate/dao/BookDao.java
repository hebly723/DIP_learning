package com.graduate.dao;

import com.graduate.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hisen on 17-4-24.
 */
public interface BookDao {
  Book queryById(long id);
  List<Book> queryAll(@Param("offset") int offset, @Param("limit") int limit);
  int addBook(Book book);
  int updateBook(Book book);
  int deleteBookById(long id);
}
