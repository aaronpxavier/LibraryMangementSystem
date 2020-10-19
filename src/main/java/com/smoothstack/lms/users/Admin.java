package com.smoothstack.lms.users;

import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Publisher;
import com.smoothstack.lms.menu.LMSAdminMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.service.BookService;
import com.smoothstack.lms.service.GoogleBooksService;
import java.sql.SQLException;
import java.util.*;

public class Admin extends User {
    public Admin() {
    }

    private void printBooks(List<Book> books, boolean inReverse) {
        int i;
        Iterator<Book> iterator;
        iterator = inReverse ? ((LinkedList<Book>)books).descendingIterator() : books.iterator();
        i = books.size();
        System.out.println("-------------------------------------------------------------------------------------------");
        while (iterator.hasNext()) {
            System.out.println("Item#: " + i);
            System.out.println(iterator.next());
            System.out.println("-------------------------------------------------------------------------------------------");
            --i;
        }
    }

    private boolean selectBook(List<Book> books, Scanner scanner) {
        Book selectedBook;
        String menuOption;
        System.out.println("Select book you wish to add or enter new for new search or quit to go back to previous menu:");
        menuOption = getNextString(scanner);
        if (menuOption.toLowerCase().compareTo("new") == 0 || menuOption.toLowerCase().compareTo("quit") == 0)
            return false;
        selectedBook = books.get(Integer.parseInt(menuOption) - 1);
        System.out.println("You Selected Book: \n" + selectedBook);
//        System.out.println("Press Enter any character to continue");
//        scanner.nextLine();
        if(selectedBook.getPublisher() != null) {
            selectedBook.getPublisher().setAddress(new GoogleBooksService().publihserAddress(selectedBook.getIsbn()));
            if(selectedBook.getPublisher().getAddress() == null || selectedBook.getPublisher().getAddress().compareTo("") == 0) {
                System.out.println("Unable to retrieve Publisher Address");
                System.out.println("Enter Publisher Address");
                selectedBook.getPublisher().setAddress(scanner.nextLine());
                System.out.println("Enter Publisher Phone");
                selectedBook.getPublisher().setPublisherPhone(scanner.nextLine());
            }
        }
        else {
            String pubName, pubAddress, pubPhone;
            System.out.println("Enter Publisher Name");
            pubName = scanner.nextLine();
            System.out.println("Enter Publisher Address");
            pubAddress = scanner.nextLine();
            System.out.println("Enter publisher Phone");
            pubPhone = scanner.nextLine();
            selectedBook.setPublisher(new Publisher(pubName, pubAddress, pubPhone));
        }
        try {
            new BookService().addBook(selectedBook);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addBook(Scanner scanner) throws Exception {
        String menuOption = "";
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_ADD));
        while (menuOption.toLowerCase().compareTo("quit") != 0) {
            LinkedList<Book> books;
            menuStack.peek().printMenu();
            menuOption = getNextString(scanner);
            if (menuOption.toLowerCase().compareTo("quit") == 0)
                break;
            books = new GoogleBooksService().booksSearch(menuOption);
            if(books == null || books.isEmpty()) {
                System.out.println("Search returned no results. Please Try again");
                continue;
            }
            printBooks(books, true);
            if (selectBook(books, scanner))
                menuOption = "quit";
        }
        menuStack.pop();
    }

    private void deleteBooks(Scanner scanner) throws Exception {
        BookService bookService = new BookService();
        int input = 0;
        List<Book> books = bookService.getBooks();
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_DELETE));
        while(input != books.size() + 1) {
            printBooks(bookService.getBooks(), false);
            menuStack.peek().printMenu();
            input = getNextInt(scanner);
        }
        menuStack.pop();
    }

    private void booksCrudMenu(Scanner scanner) throws Exception {
        Integer menuOption = 0;
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_MAIN));
        while(menuOption != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            menuOption = getNextInt(scanner);
            if(menuOption == null)
                System.out.println("Invalid entry try again");
            else if(menuOption == 1)
                addBook(scanner);
            else if(menuOption == 3)
                deleteBooks(scanner);
        }
        menuStack.pop();
    }

    @Override
    public void start(Scanner scanner) throws Exception {
        Integer menuOption = 0;
        while (!menuStack.empty())
            menuStack.pop();
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_MAIN));
        while (menuOption != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            menuOption = getNextInt(scanner);
            if(menuOption == 1)
                booksCrudMenu(scanner);
        }
        menuStack.pop();
    }
}
