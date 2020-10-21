package com.smoothstack.lms.menu;

public class LMSMenu {

    protected final static String NEW_LINE = System.getProperty("line.separator");
    private final static String MAIN_MENU = String.join(
            NEW_LINE,
            "Welcome to the SS Library Management System. Which category of a user are you?",
            "1) Librarian",
            "2) Adminstrator",
            "3) Borrower",
            "4) Quit Program"
    );

    private static final String BRANCH_UPDATE_OPITONS = String.join(
            NEW_LINE,
            "Branch Update Options",
            "1) Update Name",
            "2) Update Address",
            "3) Commit Changes",
            "4) Quit to previous"
    );

    private final static String BORROWER_PICK_BRANCH_HEADER = "Pick the Branch you want to check out from:" + NEW_LINE;
    private final static int MAIN_SIZE = 3;

    protected MenusEnum menu;
    protected int currentMenuSize = 0;
    private static final int LB_UPDATE_OPTIONS_SIZE = 4;
    //public LMSMenu() {}

    public LMSMenu(MenusEnum menu) {
        this.menu = menu;
        setSize();
    }

    public void printMenu () {
        String msg = "";
        switch(menu) {
            case MAIN:
                msg = MAIN_MENU;
                break;
            case LB_UPDATE:
                msg = BRANCH_UPDATE_OPITONS;
                break;
        }
        System.out.println(msg);
    }

    protected int setSize() {
        switch(menu) {
            case MAIN:
                currentMenuSize = MAIN_SIZE;
                return MAIN_SIZE;
            case LB_UPDATE:
                return LB_UPDATE_OPTIONS_SIZE;
            case UPDATE_BRANCH:
                //todo
                return 0;
            case BRANCHES_SELECTOR:
                //todo
                return 0;
            case LIBRARIAN_BOOK_PICKER:
                //todo
                return 0;
            case BORROWER_BOOK_PICKER:
                //todo
                return 0;
            default:
                return 0;
        }
    }

    public int getSize() {
        return setSize();
    }
}
