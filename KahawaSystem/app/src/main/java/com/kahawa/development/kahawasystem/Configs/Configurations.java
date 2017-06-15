package com.kahawa.development.kahawasystem.Configs;


public class Configurations {



    //url for making requests for the hotels data
    public static final String NEWS_URL="http://syncsoft.or.ke/kis/kahawa_news.php?page=";
    public static  final String NEWS_TITLE="title";
    public static  final String NEWS_IMAGE_URL="icon";
    public static  final String NEWS_CONTENT="content";
    public static  final String NEWS_LOCATION="location";
    public static  final String NEWS_DATE="date";
    //public static final String NEWS_URL="http://10.0.2.2/kahawa/kahawa_news.php?page=";

    //public static final String BUY_URL="http://10.0.2.2/kahawa/buy.php?page=";
    public static final String BUY_URL="http://syncsoft.or.ke/kis/buy.php?page=";
    public static  final String BUY_NAME="goods_name";
    public static  final String BUY_IMAGE_URL="goods_image";
    public static  final String BUY_COST="goods_cost";
    public static  final String BUY_ID="goods_id";
    public static  final String BUY_DESC="goods_description";


    //URL to our login.php file
    public static final String LOGIN_URL = "http://syncsoft.or.ke/kis/login.php";
    //public static final String LOGIN_URL = "http://10.0.2.2/kahawa/login.php";
    //Keys for username and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";
    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";
    //This would be used to store te email of current logged in user
    public static final String USERNAME_SHARED_PREF = "username";
    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String CREATEDACCOUNT_SHARED_PREF = "created";



    //URL to our register.php file
    public static final String REGISTER_URL = "http://syncsoft.or.ke/kis/registeruser.php";
    //public static final String REGISTER_URL = "http://10.0.2.2/kahawa/registeruser.php";
    //Keys for email and password as defined in our $_POST['key'] in login.php

    public static final String KEY_REGISTER_NAME = "name";
    public static final String KEY_REGISTER_USERNAME = "username";
    public static final String KEY_REGISTER_PASSWORD = "password";
    //If server response is equal to this that means login is successful
    public static final String REGISTRATION_FAILURE = "username already exist";

    //URL to our register.php file
    public static final String BUY_GOODS_URL = "http://syncsoft.or.ke/kis/registeruser.php";
    //public static final String BUY_GOODS_URL = "http://10.0.2.2/kahawa/buy_goods.php";
    public static final String BUY_GOODS_NAME = "good_name";
    public static final String BUY_GOODS_COST = "good_cost";
    public static final String BUY_GOODS_DATE_BOUGHT = "date_bought";
    public static final String BUY_SUCCESSFUL = "successful";

}