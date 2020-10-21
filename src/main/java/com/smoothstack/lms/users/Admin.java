package com.smoothstack.lms.users;

import com.smoothstack.lms.entity.*;
import com.smoothstack.lms.entity.Borrower;
import com.smoothstack.lms.menu.LMSAdminMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.service.*;

import java.sql.SQLException;
import java.util.*;

public class Admin extends User {
    public Admin() {
    }

    private boolean deleteBook(Book book) {
        BookService bookService = new BookService();
        try {
            bookService.deleteBook(book);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    private void addBookMenu(Scanner scanner) {
        String menuOption = "";
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_ADD));
        while (!inputIsQuit(menuOption)) {
            LinkedList<Book> books;
            menuStack.peek().printMenu();
            menuOption = getNextString(scanner);
            if (inputIsQuit(menuOption))
                break;
            books = new GoogleBooksService().booksSearch(menuOption);
            if(books == null || books.isEmpty()) {
                System.out.println("Search returned no results. Please Try again");
                continue;
            }
            print(books, true);
            if (selectBook(books, scanner))
                menuOption = "quit";
        }
        menuStack.pop();
    }

    private boolean updateAuthor(Author author, Scanner scanner) {
        System.out.println("Current author is " + author + NEW_LINE + "Enter new name for author. Enter quit to go back to  previous");
        try {
            String input = getNextString(scanner);
            if(input.toLowerCase().compareTo("quit") != 0) {
                author.setAuthorName(input);
                new AuthorsService().updateAuthor(author);
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    private boolean updateAuthors(List<Author> authors, Scanner scanner) {
        int i = 0;
        Integer input = 0;
        System.out.println("Which author Do you want to modify>?");
        for(Author author : authors) {
            ++i;
            System.out.println(i + ") " + author);
        }
        System.out.println((i + 1) + ") Quit to previous");
        input = getNextInt(scanner);
        if(input == i + 1)
            return false;
        else if(input != null)
            return updateAuthor(authors.get(i-1), scanner);
        updateAuthors(authors, scanner); // recursive call;
        return false;
    }

    private void deleteBooksMenu(Scanner scanner) throws SQLException {
        BookService bookService = new BookService();
        String input = "";
        List<Book> books = bookService.getBooks();
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_DELETE));
        while(input.toLowerCase().compareTo("quit") != 0) {
            print(books, false);
            menuStack.peek().printMenu();
            input = getNextString(scanner);
            if(input.toLowerCase().compareTo("quit") != 0 && deleteBook(books.get(books.size() - Integer.parseInt(input)))) {
                input = "quit";
            }
        }
        menuStack.pop();
    }

    private boolean updatePublisherMenu(Publisher publisher, Scanner scanner) {
        int input = 0;
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_PUB_UPDATE_OPTIONS));
        while (input != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            System.out.println("Current Publisher: " + publisher);
            input = getNextInt(scanner);
            if (input == 1) {
                String name;
                System.out.println("Current publisher name = " );
                System.out.println("You chose to change publisher name what would you like to change it to? Enter quit to go to previous");
                name = getNextString(scanner);
                if(name.toLowerCase().compareTo("quit") != 0)
                    publisher.setName(name);
                else
                    return false;
            } else if(input == 2) {
                String address;
                System.out.println("You chose to change publisher address what would you like to change it to? Enter quit to go to previous");
                address = getNextString(scanner);
                if(address.toLowerCase().compareTo("quit") != 0)
                    publisher.setAddress(address);
                else
                    return false;
            } else if(input == 3) {
                String phone;
                System.out.println("You chose to change publisher phone what would you like to change it to? Enter quit to go to previous");
                phone = getNextString(scanner);
                if(phone.toLowerCase().compareTo("quit") != 0)
                    publisher.setPublisherPhone(phone);
                else
                    return false;
            } else if(input == 4) {
                try {
                    new PublisherService().update(publisher);
                    return true;
                } catch(SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        menuStack.pop();
        return false;
    }

    private void updateGenre(Genre genre, Scanner scanner) throws SQLException{
        System.out.println("Current Genre is : " + genre.getName() + NEW_LINE + "Enter new name for genre or type quit to exit ot previous");
        String input = getNextString(scanner);
        if(input.toLowerCase().compareTo("quit") == 0)
            return;
        genre.setName(input);
        new GenresService().updateGenre(genre);
    }

    private boolean updateGenres(List<Genre> genres, Scanner scanner) {
        int i = 0;
        int input;
        System.out.println("Select one");
        for(Genre genre : genres) {
            ++i;
            System.out.println(i + ") " + genre.getName());
        }
        System.out.println((i + 1) + ") Quit to previous");
        input = getNextInt(scanner);
        if(input == i + 1)
            return false;
        try {
            updateGenre(genres.get(input - 1), scanner);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean updateBookTitle(Book book, Scanner scanner) {
        System.out.println("Current Book is : " + NEW_LINE + book);
        System.out.println("Type new title or enter quit to go to previous");
        String input = getNextString(scanner);
        if(input.toLowerCase().compareTo("quit") == 0) {
            return false;
        } else {
            book.setTitle(input);
            try{
                new BookService().updateBook(book);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private void updateBook(Book book, Scanner scanner) {
        int input = 0;
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_UPDATE_OPTIONS));
        while (input != menuStack.peek().getSize()) {

            menuStack.peek().printMenu();
            input = getNextInt(scanner);
            if(input == 1) {
                input = updateAuthors(book.getAuthors(), scanner) ? menuStack.peek().getSize() : 0;
            } else if(input == 2) {
                input = updatePublisherMenu(book.getPublisher(), scanner) ? menuStack.peek().getSize() : 0;
            } else if(input == 3) {
                input = updateGenres(book.getGenres(), scanner) ? menuStack.peek().getSize() : 0;
            } else if(input == 4) {
                input = updateBookTitle(book, scanner) ? menuStack.peek().getSize() : 0;
            }
        }
        menuStack.pop();
    }

    private void updateBooksMenu(Scanner scanner) throws Exception {
        BookService bookService = new BookService();
        String input = "";
        List<Book> books = bookService.getBooks();
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_UPDATE));
        while(input.toLowerCase().compareTo("quit") != 0) {
            print(books, false);
            menuStack.peek().printMenu();
            input = getNextString(scanner);
            if(input.toLowerCase().compareTo("quit") == 0)
                continue;
            updateBook(books.get(books.size() - Integer.parseInt(input)), scanner);
        }
        menuStack.pop();
    }

    private void booksCrudMenu(Scanner scanner) throws Exception {
        Integer menuOption = 0;
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BOOK_MAIN));
        while(menuOption != null && menuOption != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            menuOption = getNextInt(scanner);
            if(menuOption == null)
                System.out.println("Invalid entry try again");
            else if(menuOption == 1)
                addBookMenu(scanner);
            else if(menuOption == 2)
                updateBooksMenu(scanner);
            else if(menuOption == 3)
                deleteBooksMenu(scanner);
            else if(menuOption == 4)
                print(new BookService().getBooks(), false);
        }
        menuStack.pop();
    }

    private boolean deleteGenre(Genre genre) {
        try {
            new GenresService().deleteGenre(genre);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean deletePublisher(Publisher publisher) {
        try {
            new PublisherService().delete(publisher);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean addGenre(Genre genre) {
        try {
            new GenresService().addGenre(genre);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void genresCrudMenu(Scanner scanner) throws SQLException{
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_GENRES_MAIN));
        Integer menuOption = 0;
        List<Genre> genres;
        while(menuOption != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            genres = new GenresService().getGenres();
            menuOption = getNextInt(scanner);
            if(menuOption == null)
                printInputError();
            else if(menuOption == 1) {
                String input;
                System.out.println("Enter name of new Genre");
                input = getNextString(scanner);
                if(input.toLowerCase().compareTo("quit") == 0)
                    break;
                if(addGenre(new Genre(input)))
                    menuOption = menuStack.peek().getSize();
            }
            else if(menuOption == 2) {
                print(genres, false);
                System.out.println("Select Genre to update: ");
                menuOption = getNextInt(scanner);
                updateGenre(genres.get(genres.size() - menuOption), scanner);
            } else if(menuOption == 3) {
                print(genres, false);
                System.out.println("Select Genre to delete: ");
                menuOption = getNextInt(scanner);
                deleteGenre(genres.get(genres.size() - menuOption));
            }
            else if(menuOption == 4)
                print(genres, false);
        }
        menuStack.pop();
    }

    private void publishersCrudMenu(Scanner scanner) throws SQLException {
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_PUB_MAIN));
        Integer menuOption = 0;
        List<Publisher> publishers;
        PublisherService publisherService = new PublisherService();

        while(menuOption != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            publishers = new PublisherService().getPublishers();
            menuOption = getNextInt(scanner);
            if(menuOption == null) {
                printInputError();
            } else if(menuOption == 1) {
                System.out.println("Enter Name of New publisher or quit to go to previous.");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    break;
                publisherService.addPublisher(new Publisher(input));
            } else if(menuOption == 2) {
                print(publishers, false);
                System.out.println("Select Publisher you would like to update or quit to go to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    break;
                updatePublisherMenu(publishers.get(publishers.size() - Integer.parseInt(input)), scanner);
            } else if(menuOption == 3) {
                print(publishers, false);
                System.out.println("Select Publisher you would like to delete or quit to go to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    break;
                deletePublisher(publishers.get(publishers.size() - Integer.parseInt(input)));
            } else if(menuOption == 4) {
                print(publishers, false);
            }
        }
        menuStack.pop();
    }

    boolean deleteBranch(Branch branch, Scanner scanner) {
        BranchService branchService = new BranchService();
        try {
            if (branchService.branchHasLoans(branch)) {
                System.out.println("This Branch has active loans Are you sure you want to delete? y/n");
                String input = getNextString(scanner);
                if (inputIsQuit(input))
                    return false;
                if (input.toLowerCase().compareTo("y") == 0) {
                    branchService.deleteBranch(branch);
                    return true;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private void updateBorrower(Borrower borrower, Scanner scanner) throws SQLException {
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BORROWER_UPDATE_OPTIONS));
        Integer menuOption = 0;
        List<Borrower> borrowers;
        BorrowerService borrowerService = new BorrowerService();
        while(menuOption != menuStack.peek().getSize()) {
            if(menuOption == null)
                printInputError();
            if(menuOption == 1) {
                System.out.println("Enter new name or quit to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input) || input == null)
                    continue;
                borrower.setName(input);
                borrowerService.updateBorrower(borrower);
            }
            else if(menuOption == 2) {
                System.out.println("Enter new address or quit to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input) || input == null)
                    continue;
                borrower.setAddress(input);
                borrowerService.updateBorrower(borrower);
            } else if(menuOption == 3) {
                System.out.println("Enter new phone or quit to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input) || input == null)
                    continue;
                borrower.setPhone(input);
                borrowerService.updateBorrower(borrower);
            }
         }
        menuStack.pop();
    }

    public void deleteBorrower(Borrower borrower, Scanner scanner) {
        try {
            BorrowerService borrowerService = new BorrowerService();
            if(!borrowerService.getLoans(borrower).isEmpty()) {
                System.out.println("Borrower has laons are you sure you want to proceed? y/n");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    return;
                if(input.toLowerCase().compareTo("y") == 0)
                    borrowerService.deleteBorrower(borrower);
            } else {
                borrowerService.deleteBorrower(borrower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void BorrowerCrudMenu (Scanner scanner) throws SQLException {
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_BORROWER_MAIN));
        Integer menuOption = 0;
        List<Borrower> borrowers;
        BorrowerService borrowerService = new BorrowerService();

        while(menuOption != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            borrowers = borrowerService.getBorrowers();
            menuOption = getNextInt(scanner);
            if(menuOption == null) {
                printInputError();
            } else if(menuOption == 1) {
                System.out.println("Enter Name of new Borrower or quit to go to previous.");
                String name = getNextString(scanner);
                System.out.println("Enter Address of new Borrower or quit to go to previous.");
                String address = getNextString(scanner);
                System.out.println("Enter phone for new borrower or quit to go to previous");
                String phone = getNextString(scanner);
                if(inputIsQuit(name) || inputIsQuit(address) || inputIsQuit(phone))
                    break;
                borrowerService.addBorrower(new Borrower(name, address, phone));
            } else if(menuOption == 2) {
                print(borrowers, false);
                System.out.println("Select Borrower you would like to update or quit to go to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    break;
                updateBorrower(borrowers.get(borrowers.size()-Integer.parseInt(input)), scanner);
            } else if(menuOption == 3) {
                this.print(borrowers, false);
                System.out.println("Select Borrower you would like to delete or quit to go to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    break;
                deleteBorrower(borrowers.get(borrowers.size()-Integer.parseInt(input)), scanner);
            } else if(menuOption == 4) {
                this.print(borrowers, false);
            }
        }
        menuStack.pop();
    }

    private void LBCrudMenu (Scanner scanner) throws SQLException {
        menuStack.push(new LMSAdminMenu(MenusEnum.ADMIN_LB_MAIN));
        Integer menuOption = 0;
        List<Branch> branches;
        BranchService branchService = new BranchService();

        while(menuOption != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            branches = branchService.getBranches();
            menuOption = getNextInt(scanner);
            if(menuOption == null) {
                printInputError();
            } else if(menuOption == 1) {
                System.out.println("Enter Name of new Branch or quit to go to previous.");
                String name = getNextString(scanner);
                System.out.println("Enter Address of new Branch or quit to go to previous.");
                String address = getNextString(scanner);
                if(inputIsQuit(name) || inputIsQuit(address))
                    break;
                branchService.addBranch(new Branch(name, address));
            } else if(menuOption == 2) {
                print(branches, false);
                System.out.println("Select Branch you would like to update or quit to go to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    break;
                updateBranchMenu(branches.get(Integer.parseInt(input) -1), scanner);
            } else if(menuOption == 3) {
                this.print(branches, false);
                System.out.println("Select Branch you would like to delete or quit to go to previous");
                String input = getNextString(scanner);
                if(inputIsQuit(input))
                    break;
                deleteBranch(branches.get(branches.size() - Integer.parseInt(input)), scanner);
            } else if(menuOption == 4) {
                this.print(branches, false);
            }
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
            else if(menuOption == 2)
                genresCrudMenu(scanner);
            else if(menuOption == 3)
                publishersCrudMenu(scanner);
            else if(menuOption == 4)
                LBCrudMenu(scanner);
            else if(menuOption == 5)
                BorrowerCrudMenu(scanner);
            else if(menuOption == 6) continue;
        }
        menuStack.pop();
    }
}
