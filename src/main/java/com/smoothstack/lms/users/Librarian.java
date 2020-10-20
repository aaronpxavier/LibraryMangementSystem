package com.smoothstack.lms.users;

import com.ibm.dtfj.javacore.parser.framework.scanner.IScanner;
import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Branch;
import com.smoothstack.lms.menu.LMSLibrarianMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.service.BranchService;

import java.sql.SQLException;
import java.util.Scanner;


public class Librarian extends User {
    private void addCopiesToBranch(Branch branch, Book book, Scanner scanner) {
        try {
            BranchService branchService = new BranchService();
            Integer numberOfCopies = branchService.existingNumberOfCopies(branch, book);
            if(numberOfCopies == null)
                numberOfCopies = 0;
            System.out.println("Existing number of copies: " + numberOfCopies);
            System.out.println("Enter new number of copies");
            int input = getNextInt(scanner);
            branchService.updateBranchCopies(branch, book, input);
            System.out.println("Succesfully updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void options(Branch branch, Scanner scanner) {
        Integer menuOptions = 0;
        menuStack.push(new LMSLibrarianMenu(MenusEnum.LIBRARIAN_OPTIONS));
            while (menuOptions != menuStack.peek().getSize()) {
                menuStack.peek().printMenu();
                menuOptions = getNextInt(scanner);
                if(menuOptions == null)
                    printInputError();
                else if(menuOptions == 1) {
                    updateBranchMenu(branch, scanner);
                } else if(menuOptions == 2) {
                    Book book = selectBook("Pick the Book you want to add copies of, to branch", scanner);
                    if(book != null)
                        addCopiesToBranch(branch, book, scanner);
                }
            }
        menuStack.pop();
    }

    @Override
    public void start(Scanner scanner) {
        int menuOptions = 0;
        while(!menuStack.isEmpty())
            menuStack.pop();
        menuStack.push(new LMSLibrarianMenu(MenusEnum.MAIN_LIBRARIAN));
        while(menuOptions != menuStack.peek().getSize()) {
            try {
                menuStack.peek().printMenu();
                menuOptions = scanner.nextInt();
                if (menuOptions == 1) {
                    Branch branch = selectBranch("Select Branch you Manage", scanner);
                    if(branch == null)
                        break;
                    options(branch, scanner);
                } else if (menuOptions != menuStack.peek().getSize()) {
                    printInputError();
                }
            } catch(Exception e) {
                e.printStackTrace();
                printIntInputError();
                scanner.nextLine();
            }
        }
        menuStack.pop();
    }
}
