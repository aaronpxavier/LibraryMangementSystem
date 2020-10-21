package com.smoothstack.lms.menu;

public class LMSLibrarianMenu extends LMSMenu{
    public LMSLibrarianMenu(MenusEnum menu) { super(menu); }

    private final static String LIBRARIAN_MENU = String.join(
            NEW_LINE,
            "1) Enter Branch you manage",
            "2) Quit to previous"
    );
    private final static String LIBRARIAN_OPTIONS_MENU = String.join(
            NEW_LINE,
            "1) Update the details of the Library",
            "2) Add Copies of Book to the Branch",
            "3) Quit to previous"
    );
    private final static String UPDATE_BRANCH_MENU = String.join(
            NEW_LINE,
            "You have chosen to update the Branch with Branch Name: X and Branch Address: City, State"
    );
    private final static String LIBRARIAN_PICK_BOOK_HEADER = "Pick the Book you want to add copies of, to your branch:" + NEW_LINE;
    private final static int LIBRARIAN_MAIN_SIZE = 2;
    private final static int LIBRARIAN_OPTIONS_SIZE = 3;
    public void printMenu () {
        String msg = "";
        switch(menu) {
            case MAIN_LIBRARIAN:
                msg = LIBRARIAN_MENU;
                break;
            case LIBRARIAN_OPTIONS:
                msg = LIBRARIAN_OPTIONS_MENU;
                break;
            case UPDATE_BRANCH:
                msg = UPDATE_BRANCH_MENU;
                break;
        }
        System.out.println(msg);
    }

    protected int setSize() {
        switch(menu) {
            case MAIN_LIBRARIAN:
                return LIBRARIAN_MAIN_SIZE;
            case LIBRARIAN_OPTIONS:
                return LIBRARIAN_OPTIONS_SIZE;
            default:
                return 0;
        }
    }
}
