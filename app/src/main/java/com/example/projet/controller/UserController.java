package com.example.projet.controller;


public final class UserController {

    private static UserController instance=new UserController();
    private UserController() {
        super();
    }
    public final static UserController getInstance(){
        if (UserController.instance==null){
            UserController.instance=new UserController();
        }
        return UserController.instance;
    }

}
