package com.smoothstack.lms.users;

import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Branch;
import com.smoothstack.lms.entity.Loan;
import com.smoothstack.lms.menu.LMSBorrowerMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.service.BorrowerService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Borrower extends User{



    @Override
    public void start() throws Exception {
        Scanner scanner= new Scanner(System.in);
        if(!menuStack.isEmpty())
            menuStack.pop();
        boolean notAuthenticated = true;
        int menuOption = 0;
        Integer cardNo = null;
        BorrowerService borrowerService = new BorrowerService();
        while(notAuthenticated) {
            System.out.println("Enter you card number or enter -1 to exit");
            cardNo = getNextInt(scanner);
            if(cardNo == -1)
                return;
            try {
                notAuthenticated = !borrowerService.checkCardNo(cardNo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        menuStack.push(new LMSBorrowerMenu(MenusEnum.BORROWER_MAIN));
        try {
            com.smoothstack.lms.entity.Borrower borrower = borrowerService.getBorrower(cardNo);
            while (menuOption != menuStack.peek().getSize()) {
                menuStack.peek().printMenu();
                menuOption = getNextInt(scanner);
                Book book;
                if(menuOption == 1) {
                    Branch branch = selectBranch("Pick the Branch you want to check out from: ", scanner);
                    if(branch == null)
                        continue;
                    book = selectBook("Pick the book you want to checkout: ", branch, scanner);
                    borrowerService.checkoutBook(borrower, book, branch);
                } if (menuOption == 2) {
                    List<Loan> bookLoans = new BorrowerService().getLoans(borrower);
                    List<Book> books = new ArrayList<>();
                    for(Loan loan : bookLoans) {
                        books.add(loan.getBook());
                    }
                    if(books.isEmpty()) {
                        System.out.println("No Books Checked out");
                    }
                    else {
                        book = selectBook("Pick the book you want to checkIn", books, scanner);
                        borrowerService.checkInBook(book, borrower);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        menuStack.pop();
    }
}
