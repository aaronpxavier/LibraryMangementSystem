package com.smoothstack.lms;

import com.smoothstack.lms.menu.LMSMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.users.Admin;
import com.smoothstack.lms.users.Borrower;
import com.smoothstack.lms.users.Librarian;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void printMain() {
        LMSMenu mainMenu = new LMSMenu(MenusEnum.MAIN);
        Librarian librarian = new Librarian();
        Admin admin = new Admin();
        Borrower borrower = new Borrower();
        int menuOption = 0;

        while (menuOption != 4) {
            try {
                mainMenu.printMenu();
                menuOption = scanner.nextInt();
                scanner.nextLine();
                if (menuOption == 1) {
                    librarian.start(scanner);
                } else if (menuOption == 2) {
                    admin.start(scanner);
                } else if (menuOption == 3) {
                    borrower.start(scanner);
                } else if (menuOption != 4) {
                    System.err.println("Invalid entry! Try Again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        printMain();
        //new GoogleBooksService().publihserAddress("0451526538");
    }
}
