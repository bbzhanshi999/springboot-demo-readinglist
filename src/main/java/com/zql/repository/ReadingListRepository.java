package com.zql.repository;

import com.zql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
public interface ReadingListRepository extends JpaRepository<Book,Long> {

    List<Book> findByReader(String reader);
}
