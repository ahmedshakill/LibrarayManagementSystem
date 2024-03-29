package com.example.lms.repository;

import com.example.lms.entity.BookWithDate;
import com.example.lms.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUserId(long id);

    List<Transaction> findAllByBooksWithDateIn(List<BookWithDate> booksWithDate);
}
