package com.kcterala.expensetrackerapi.service;

import com.kcterala.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable pageable);
    Expense getExpenseById(Long id);
    void deleteExpenseById(Long id);
    Expense saveExpense(Expense expense);

    Expense updateExpense(Long id, Expense expense);

    List<Expense> readByCategory(String category, Pageable page);

    List<Expense> readByNameContaining(String name, Pageable pageable);

    List<Expense> readByDate(Date startDate, Date endDate, Pageable pageable);
 }
