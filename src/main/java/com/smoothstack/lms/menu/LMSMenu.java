package com.smoothstack.lms.menu;

public class LMSMenu {

    private final static String NEW_LINE = System.getProperty("line.separator");
    private final static String MAIN_MENU = String.join(
            NEW_LINE,
            "Welcome to the SS Library Management System. Which category of a user are you?",
            "1) Librarian",
            "2) Adminstrator",
            "3) Borrower",
            "4) Quit Program"
    );
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
            "You have chosen to update the Branch with Branch Name: X and Branch Address: City, State",
            "You have chosen to update the Branch with Branch Id: X and Branch Name: ABCD. Enter ‘quit’ at any prompt to cancel operation."
    );
    private final static String LIBRARIAN_PICK_BOOK_HEADER = "Pick the Book you want to add copies of, to your branch:" + NEW_LINE;
    private final static String BORROWER_PICK_BRANCH_HEADER = "Pick the Branch you want to check out from:" + NEW_LINE;
    private final static int MAIN_SIZE = 3;
    private final static int LIBRARIAN_MAIN_SIZE = 2;
    private final static int LIBRARIAN_OPTIONS_SIZE = 0;
    protected MenusEnum menu;
    private int currentMenuSize = 0;

    //public LMSMenu() {}

    public LMSMenu(MenusEnum menu) {
        this.menu = menu;
    }

    protected static void printMenu (MenusEnum menu) throws Exception {
        String msg = "";
        switch(menu) {
            case MAIN:
                msg = MAIN_MENU;
                break;
            case MAIN_LIBRARIAN:
                msg = LIBRARIAN_MENU;
                break;
            case LIBRARIAN_OPTIONS:
                msg = LIBRARIAN_OPTIONS_MENU;
                break;
            case UPDATE_BRANCH:
                msg = UPDATE_BRANCH_MENU;
                break;
            case BRANCHES_SELECTOR:
                msg = branchOptions();
                break;
            case LIBRARIAN_BOOK_PICKER:
                msg = LIBRARIAN_PICK_BOOK_HEADER + bookOptions();
                break;
            case BORROWER_BOOK_PICKER:
                msg = BORROWER_PICK_BRANCH_HEADER + bookOptions();
            default:
                throw new Exception("INVALID MENU OPTION");
        }
        System.out.println(msg);
    }

    private static int size(MenusEnum menu) {
        switch(menu) {
            case MAIN:
                return MAIN_SIZE;
            case MAIN_LIBRARIAN:
                return LIBRARIAN_MAIN_SIZE;
            case LIBRARIAN_OPTIONS:
                return LIBRARIAN_OPTIONS_SIZE;
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

    private static String branchOptions() {
        //todo
        return "";
    }

    private static String bookOptions() {
        //todo
        return "";
    }

    public void printMenu() {
        try {
            printMenu(menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return size(menu);
    }
}
