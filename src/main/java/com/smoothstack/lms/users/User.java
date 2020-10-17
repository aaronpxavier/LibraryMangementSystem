package com.smoothstack.lms.users;

import com.smoothstack.lms.menu.LMSMenu;

import java.util.Stack;

public abstract class User {
    protected Stack<LMSMenu> menuStack = new Stack();
    User() {}

    protected abstract void start();

    protected void printInvalidEntry() {

    }
}
