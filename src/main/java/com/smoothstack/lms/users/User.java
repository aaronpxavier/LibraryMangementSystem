package com.smoothstack.lms.users;

import com.smoothstack.lms.menu.LMSMenu;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public abstract class User {

    protected Stack<LMSMenu> menuStack = new Stack<>();
    User() {}

    protected abstract void start(Scanner scanner) throws Exception;

    protected void printIntInputError() { System.out.println("Invalid entry! Must be valid Integer type"); }

    protected void printInputError() { System.out.println("Invalid entry! Try Again."); }

    protected Integer getNextInt(Scanner scanner) {
        Integer input = null;
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            printIntInputError();
            return null;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    protected String getNextString(Scanner scanner) {
        String input = null;
        try {
            input = scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            printIntInputError();
            return null;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return input;
    }

}
