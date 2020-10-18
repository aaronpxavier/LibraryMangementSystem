package com.smoothstack.lms.entity;

import java.util.Date;

public class Loan {
    Book book;
    Branch branch;
    Borrower borrower;
    Date dateOut;
    Date dueDate;
    Date dateIn;

    public Loan(Book book, Branch branch, Borrower borrower) {
        this.book = book;
        this.branch = branch;
        this.borrower = borrower;
    }

    public Loan() { }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }
}
