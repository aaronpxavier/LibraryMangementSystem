package com.smoothstack.lms.entity;

import java.util.List;

public class Branch {
    Integer id;
    String name;
    String address;
    List<Loan> loans;
    List<BookCopies> booksOwned;

    public List<BookCopies> getBooksOwned() {
        return booksOwned;
    }

    public void setBooksOwned(List<BookCopies> booksOwned) {
        this.booksOwned = booksOwned;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }


    public Branch(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
