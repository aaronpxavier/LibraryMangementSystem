package com.smoothstack.lms.menu;

public class LMSAdminMenu extends LMSMenu{
    private static final String ADMIN_MAIN_MENU = String.join(
            NEW_LINE,
            "Welcome to the Admin Menu. Make a Selection",
            "1) Book Crud Operations",
            "2) Genres Crud Operations",
            "3) Publishers Crud Operations",
            "4) Library Crud Operations"
    );
    public LMSAdminMenu(MenusEnum menu) {
        super(menu);
    }


}
