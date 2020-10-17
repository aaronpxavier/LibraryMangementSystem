package com.smoothstack.lms.users;

import com.smoothstack.lms.menu.LMSMenu;
import com.smoothstack.lms.menu.MenusEnum;


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
        while(menuOptions != menuStack.peek().getSize()) {
            menuStack.peek().printMenu();
            try {
                menuOptions = scanner.nextInt();
                if (menuOptions == 1) {
                    pickBranch();
                } else if (menuOptions != menuStack.peek().getSize()) {
                    System.out.println("Invalid entry! Try Again.");
                }
            } catch(Exception e) {
                System.out.println("Invalid entry must be valid Integer");
                scanner.nextLine();
            }
        }
        menuStack.pop();
    }
}
