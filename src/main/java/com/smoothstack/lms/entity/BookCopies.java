package com.smoothstack.lms.entity;

public class BookCopies {
    Book book;
    Branch branch;
    Integer noOfCopies;

    public BookCopies(Book book, Branch branch, Integer noOfCopies) {
        this.book = book;
        this.branch = branch;
        this.noOfCopies = noOfCopies;
    }

    public BookCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

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

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }
}
