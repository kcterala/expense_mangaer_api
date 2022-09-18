package com.kcterala.expensetrackerapi.controller;

import com.kcterala.expensetrackerapi.entity.Expense;
import com.kcterala.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable pageable)  {
        return expenseService.getAllExpenses(pageable).toList();
    }


    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam Long id){
        expenseService.deleteExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense){
        return expenseService.saveExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id){
        return expenseService.updateExpense(id,expense);
    }
    @GetMapping("/expenses/category")
    public List<Expense> getExpensesByCategory(@RequestParam String category,Pageable page){
        return expenseService.readByCategory(category,page);    }

    @GetMapping("/expenses/name")
    public List<Expense> readByName(@RequestParam String name, Pageable page){
        return expenseService.readByNameContaining(name,page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getExpensesByDates(@RequestParam(required = false) Date startDate,@RequestParam(required = false) Date endDate, Pageable pageable){
        return expenseService.readByDate(startDate,endDate,pageable);
    }
}
