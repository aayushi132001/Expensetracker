package Expense_Tracker.expensetracker;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    public Expense addExpense(Expense expense) {
        return repo.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }

    public Expense getExpenseById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Expense updateExpense(String id, Expense updatedExpense) {
        Expense expense = repo.findById(id).orElse(null);
        if (expense != null) {
            expense.setName(updatedExpense.getName());
            expense.setAmount(updatedExpense.getAmount());
            expense.setCategory(updatedExpense.getCategory());
            expense.setDate(updatedExpense.getDate());
            return repo.save(expense);
        }
        return null;
    }

    public String deleteExpense(String id) {
        repo.deleteById(id);
        return "Expense deleted: " + id;
    }
}
