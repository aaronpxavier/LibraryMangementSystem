package com.smoothstack.lms.users;

import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Branch;
import com.smoothstack.lms.menu.LMSAdminMenu;
import com.smoothstack.lms.menu.LMSMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.service.BookService;
import com.smoothstack.lms.service.BorrowerService;
import com.smoothstack.lms.service.BranchService;

import java.sql.SQLException;
import java.util.*;

public abstract class User {

    protected Stack<LMSMenu> menuStack = new Stack<>();
    User() {}

    protected final static String NEW_LINE = System.getProperty("line.separator");

    protected abstract void start(Scanner scanner) throws Exception;

    protected void printIntInputError() { System.out.println("Invalid entry! Must be valid Integer type"); }

    protected void printInputError() { System.out.println("Invalid entry! Try Again."); }

    protected Integer getNextInt(Scanner scanner) {
        Integer input = null;
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            printIntInputError();
            return null;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    protected String getNextString(Scanner scanner) {
        String input = null;
        try {
            input = scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            printIntInputError();
            return null;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    protected boolean inputIsQuit(String input) {
        return input.toLowerCase().compareTo("quit") == 0;
    }

    protected <T> void print(List<T> list, boolean inReverse) {
        int i;
        Iterator<T> iterator;
        if(list.isEmpty())
            return;
        iterator = inReverse ? ((LinkedList<T>)list).descendingIterator() : list.iterator();
        i = list.size();
        System.out.println("-------------------------------------------------------------------------------------------");
        while (iterator.hasNext()) {
            System.out.println("Item#: " + i);
            System.out.println(iterator.next());
            System.out.println("-------------------------------------------------------------------------------------------");
            --i;
        }
    }

    protected Branch selectBranch(String header, Scanner scanner) {
        try{
            List<Branch> branches = new BranchService().getBranches();
            print(branches, false);
            System.out.println((branches.size() + 1) + ") Quit to Previous" + NEW_LINE + "Select Branch");
            int input = getNextInt(scanner);
            return branches.get(branches.size() - input);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Book selectBook(String header, Scanner scanner){
        try{
            List<Book> books = new BookService().getBooks();
            if(header != null)
                System.out.println(header);
            return selectBook(header, books, scanner);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Book selectBook(String header, List<Book> books, Scanner scanner) {
        if(header != null)
            System.out.println(header);
        print(books, false);
        System.out.println("Type Quit to Previous" + NEW_LINE + "Select Book");
        String input = getNextString(scanner);
        if(inputIsQuit(input))
            return null;
        return books.get(books.size() - Integer.parseInt(input));
    }

    protected Book selectBook(String header, Branch branch, Scanner scanner) {
        try{
            List<Book> books = new BookService().getBooksInBranch(branch);
            if(header != null)
                System.out.println(header);
            print(books, false);
            System.out.println((books.size() + 1) + ") Quit to Previous" + NEW_LINE + "Select Branch");
            String input = getNextString(scanner);
            if(inputIsQuit(input))
                return null;
            return books.get(books.size() - Integer.parseInt(input));
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    protected void updateBranchMenu(Branch branch, Scanner scanner) {
        System.out.println("Please enter new branch name or enter N/A for no change");
        String branchName = getNextString(scanner);
        if(inputIsQuit(branchName) || branchName.toLowerCase().compareTo("n/a") == 0)
            return;
        System.out.println("Please enter new branch address or enter N/A for no change");
        String branchAddress = getNextString(scanner);
        if(inputIsQuit(branchAddress) || branchAddress.toLowerCase().compareTo("n/a") == 0)
            return;
        try {
            branch.setName(branchName);
            branch.setAddress(branchAddress);
            new BranchService().updateBranch(branch);
            System.out.println("Successfully Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
