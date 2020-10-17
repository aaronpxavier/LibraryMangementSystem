package com.smoothstack.lms.users;

import com.smoothstack.lms.menu.LMSMenu;

import java.util.Scanner;
import java.util.Stack;

public abstract class User {
    protected static Scanner scanner = new Scanner(System.in);
    protected Stack<LMSMenu> menuStack = new Stack();
    User() {}

    protected abstract void start();

}
