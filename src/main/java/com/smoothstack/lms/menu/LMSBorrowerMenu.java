package com.smoothstack.lms.menu;

public class LMSBorrowerMenu extends LMSMenu {
    private static final String BORROWER_MAIN_MENU = String.join(
            NEW_LINE,
            "1) Check out a book",
            "2) Return a Book",
            "3) Quit to Previous"
    );
    private static final int BORROWER_MAIN_SIZE = 3;

    public LMSBorrowerMenu(MenusEnum menu) {
        super(menu);
    }

    public void printMenu () {
        String msg = "";
        switch(menu) {
            case BORROWER_MAIN:
                msg = BORROWER_MAIN_MENU;
                break;
        }
        System.out.println(msg);
    }

    protected int setSize() {
        switch(menu) {
            case BORROWER_MAIN:
                return BORROWER_MAIN_SIZE;
            default:
                return 0;
        }
    }
}
