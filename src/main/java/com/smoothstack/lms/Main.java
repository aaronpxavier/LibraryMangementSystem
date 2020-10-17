package com.smoothstack.lms;


import com.smoothstack.lms.menu.LMSMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.users.Admin;
import com.smoothstack.lms.users.Borrower;
import com.smoothstack.lms.users.Librarian;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LMSMenu mainMenu = new LMSMenu(MenusEnum.MAIN);
        Librarian librarian = new Librarian();
        Admin admin = new Admin();
        Borrower borrower = new Borrower();
        int menuOption = 0;

        while (menuOption != 4) {
            mainMenu.printMenu();
            menuOption = scanner.nextInt();
            if (menuOption == 1) {
                librarian.start();
            } else if (menuOption == 2) {
                admin.start();
            } else if (menuOption == 3) {
                borrower.start();
            } else if (menuOption != 4) {
                System.err.println("Invalid entry! Try Again.");
            }
        }
    }
}
