package com.smoothstack.lms.menu;

public class LMSAdminMenu extends LMSMenu{
    private static final String ADMIN_MAIN_MENU = String.join(
            NEW_LINE,
            "Welcome to the Admin Menu. Make a Selection",
            "1) Book Crud Operations",
            "2) Genres Crud Operations",
            "3) Publishers Crud Operations",
            "4) Library Crud Operations",
            "5) Borrowers Crud Operations",
            "6) Over-ride Due Date for a Book Loan",
            "7) Quit to previous"
    );

    private static final String CRUD_OPTIONS = String.join(
            NEW_LINE,
            "1) Add",
            "2) Update",
            "3) Delete",
            "4) Read",
            "5) Quit to previous"
    );

    private static final String BOOK_UPDATE_OPTIONS = String.join(
            NEW_LINE,
            "Book Update Options",
            "1) Update Book Authors",
            "2) Update Book Publisher",
            "3) Update Book Genre",
            "4) Update Book Title",
            "5) Quit to previous"
    );

    private static final String BORROWER_UPDATE_OPTIONS = String.join(
            NEW_LINE,
            "Borrower Update Options",
            "1) Update Borrower Name",
            "2) Update Borrower Address",
            "3) Update Borrower Phone",
            "4) Quit to previous"
    );

    private static final String PUBLISHER_UPDATE_OPTIONS = String.join(
            NEW_LINE,
            "Publisher Update Options",
            "1) Change Publisher Name",
            "2) Change Publisher Address",
            "3) Change Publisher Phone",
            "4) Commit Changes to Publisher",
            "5) Quit to previous"
    );



    private static final String BOOK_CRUD_HEADER = "Book CRUD operations. Make Selection" + NEW_LINE;
    private static final String GENRES_CRUD_HEADER = "Genres CRUD operations. Make Selection" + NEW_LINE;
    private static final String PUB_CRUD_HEADER = "Publishers CRUD operations. Make Selection" + NEW_LINE;
    private static final String LB_CRUD_HEADER = "Library Branches CRUD operations. Make Selection" + NEW_LINE;
    private static final String BORROWERS_CRUD_HEADER = "Borrowers operations. Make Selection" + NEW_LINE;
    private static final String SELECT_BOOK_TO_ADD = "Please Enter Name of Book You Would Like to add or type quit to exit to previous: ";
    private static final String BOOK_CRUD_DELETE_HEADER = "Select Book You want to Delete or type quit to exit to previous:" + NEW_LINE;
    private static final String BOOK_CRUD_UPDATE_HEADER = "Select Book You want to Update or type quit to exit to previous" + NEW_LINE;
    private static final int ADMIN_MENU_SIZE = 7;
    private static final int ADMIN_CRUD_MAIN_SIZE = 5;
    private static final int BOOK_UPDATE_OPTIONS_SIZE = 5;
    private static final int PUB_UPDATE_OPTIONS_SIZE = 5;

    private static final int BORROWER_UPDATE_OPTIONS_SIZE = 4;
    public LMSAdminMenu(MenusEnum menu) { super(menu); }

    public void printMenu () {
        String msg = "";
        switch (menu) {
            case ADMIN_MAIN:
                msg = ADMIN_MAIN_MENU;
                break;
            case ADMIN_BOOK_MAIN:
                msg = BOOK_CRUD_HEADER + CRUD_OPTIONS;
                break;
            case ADMIN_GENRES_MAIN:
                msg = GENRES_CRUD_HEADER + CRUD_OPTIONS;
                break;
            case ADMIN_PUB_MAIN:
                msg = PUB_CRUD_HEADER + CRUD_OPTIONS;
                break;
            case ADMIN_LB_MAIN:
                msg = LB_CRUD_HEADER + CRUD_OPTIONS;
                break;
            case ADMIN_BORROWER_MAIN:
                msg = BORROWERS_CRUD_HEADER + CRUD_OPTIONS;
                break;
            case ADMIN_BOOK_ADD:
                msg = SELECT_BOOK_TO_ADD;
                break;
            case ADMIN_BOOK_DELETE:
                msg = BOOK_CRUD_DELETE_HEADER;
                break;
            case ADMIN_BOOK_UPDATE:
                msg = BOOK_CRUD_UPDATE_HEADER;
                break;
            case ADMIN_BOOK_UPDATE_OPTIONS:
                msg = BOOK_UPDATE_OPTIONS;
                break;
            case ADMIN_PUB_UPDATE_OPTIONS:
                msg = PUBLISHER_UPDATE_OPTIONS;
                break;
            case ADMIN_BORROWER_UPDATE_OPTIONS:
                msg = BORROWER_UPDATE_OPTIONS;
        }
        System.out.println(msg);
    }

    protected int setSize() {
        switch(menu) {
            case ADMIN_MAIN:
                currentMenuSize = ADMIN_MENU_SIZE;
                return ADMIN_MENU_SIZE;
            case ADMIN_BOOK_UPDATE_OPTIONS:
                return BOOK_UPDATE_OPTIONS_SIZE;
            case ADMIN_BORROWER_UPDATE_OPTIONS:
                return BORROWER_UPDATE_OPTIONS_SIZE;
            case ADMIN_BOOK_MAIN:
            case ADMIN_BORROWER_MAIN:
            case ADMIN_GENRES_MAIN:
            case ADMIN_LB_MAIN:
            case ADMIN_PUB_MAIN:
                return currentMenuSize = ADMIN_CRUD_MAIN_SIZE;
            case ADMIN_PUB_UPDATE_OPTIONS:
                return PUB_UPDATE_OPTIONS_SIZE;
            case ADMIN_BOOK_DELETE:
            case ADMIN_BOOK_ADD:
            case ADMIN_BOOK_UPDATE:
                return 1;
            default:
                return 0;
        }
    }
}




