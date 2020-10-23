package com.smoothstack.lms;

import com.smoothstack.lms.menu.LMSMenu;
import com.smoothstack.lms.menu.MenusEnum;
import com.smoothstack.lms.users.Admin;
import com.smoothstack.lms.users.Borrower;
import com.smoothstack.lms.users.Librarian;

import java.util.Scanner;

public class Main {

    public static void printMain() {
        LMSMenu mainMenu = new LMSMenu(MenusEnum.MAIN);
        Librarian librarian = new Librarian();
        Admin admin = new Admin();
        Borrower borrower = new Borrower();
        Scanner scanner = new Scanner(System.in);
        int menuOption = 0;

        while (menuOption != 4) {

            try {
                mainMenu.printMenu();
                menuOption = scanner.nextInt();
                scanner.nextLine();
                if (menuOption == 1) {
                    librarian.start();
                } else if (menuOption == 2) {
                    admin.start();
                } else if (menuOption == 3) {
                    borrower.start();
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
    }
}
