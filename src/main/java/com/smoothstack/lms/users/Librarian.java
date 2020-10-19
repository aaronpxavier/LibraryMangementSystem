package com.smoothstack.lms.users;

import com.smoothstack.lms.menu.LMSMenu;
import com.smoothstack.lms.menu.MenusEnum;

import java.util.Scanner;


public class Librarian extends User {

    private void pickBranch(Scanner scanner) {
        int menuOptions = 0;
        menuStack.push(new LMSMenu(MenusEnum.BRANCHES_SELECTOR));
        while(menuOptions != menuStack.peek().getSize()) {

        }
        menuStack.pop();
    }

    @Override
    public void start(Scanner scanner) {
        int menuOptions = 0;
        menuStack.push(new LMSMenu(MenusEnum.MAIN_LIBRARIAN));
        while(menuOptions != menuStack.peek().getSize()) {
            try {
                menuStack.peek().printMenu();
                menuOptions = scanner.nextInt();
                if (menuOptions == 1) {
                    pickBranch(scanner);
                } else if (menuOptions != menuStack.peek().getSize()) {
                    printInputError();
                }
            } catch(Exception e) {
                printIntInputError();
                scanner.nextLine();
            }
        }
        menuStack.pop();
    }
}
