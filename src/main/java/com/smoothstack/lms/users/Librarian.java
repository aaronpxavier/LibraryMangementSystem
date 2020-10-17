package com.smoothstack.lms.users;

import com.smoothstack.lms.menu.LMSMenu;
import com.smoothstack.lms.menu.MenusEnum;

import java.util.Scanner;

public class Librarian extends User {

    private void pickBranch() {
        int menuOptions = 0;
        menuStack.push(new LMSMenu(MenusEnum.BRANCHES_SELECTOR));
        while(menuOptions != menuStack.peek().getSize()) {

        }
        menuStack.pop();
    }

    @Override
    public void start() {
        int menuOptions = 0;
        menuStack.push(new LMSMenu(MenusEnum.MAIN_LIBRARIAN));
        Scanner scanner = new Scanner(System.in);
        while(menuOptions != 2) {
            menuStack.peek().printMenu();
            try {
                menuOptions = scanner.nextInt();
                if (menuOptions == 1) {
                    pickBranch();
                } else if (menuOptions != menuStack.peek().getSize()) {
                    System.out.println("Invalid entry! Try Again.");
                }
            } catch(Exception e) {
                System.out.println("Invalid Entry must be valid Integer");
                scanner.nextLine();
            }
        }
        menuStack.pop();
    }
}
