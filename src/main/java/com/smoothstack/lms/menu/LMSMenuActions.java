//package com.smoothstack.lms;
//
//import java.util.Stack;
//
//public class LMSMenuActions extends LMSMenu{
//    public enum Users {
//        LIBRARIAN,
//        BORROWER,
//        ADMIN
//    }
//
//    private static Stack<LMSMenuActions> menuStack = new Stack ();
//    private Users user;
//
//    LMSMenuActions (Users user) {
//        if (user == Users.BORROWER) {
//            this.user = Users.BORROWER;
//        } else if (user == Users.LIBRARIAN) {
//            this.user = Users.LIBRARIAN;
//        } else if (user == Users.ADMIN) {
//            this.user = Users.BORROWER;
//        }
//    }
//}
