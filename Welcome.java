package views;
import DAO.userDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.sql.SQLException;
import java.util.*;
import java.util.Scanner;

public class Welcome {
 public void welcomescreen(){
     Scanner ss=new Scanner(System.in);
     System.out.println("welcome to the app");
     System.out.println("press 1 to login");
     System.out.println("press 2 to sign up");
     System.out.println("press 3 to exit");
     int choice= ss.nextInt();
     switch (choice){
         case 1->login();
         case 2->signup();
         case 3-> System.exit(3);
     }
     
 }

    private void login() {
     Scanner ss=new Scanner(System.in);
        System.out.println("enter email");
     String email= ss.nextLine();
     try{
         if (userDAO.isExists(email)){
             String genOTP= GenerateOTP.getOTP();
             SendOTPService.sendOTP(email,genOTP);
             System.out.println("enter the opt");
             String opt=ss.nextLine();
             if(opt.equals(genOTP)){
                 new UserView(email).Home();

             }else {
                 System.out.println("wrong otp");
             }
         }else {
             System.out.println("user not found");
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
    }

    private void signup() {
     Scanner ss=new Scanner(System.in);
        System.out.println("enter name");
     String name=ss.nextLine();
        System.out.println("enter email");
        String email=ss.nextLine();
        String genOTP= GenerateOTP.getOTP();
        SendOTPService.sendOTP(email,genOTP);
        System.out.println("enter the opt");
        String otp=ss.nextLine();
        if (otp.equals(genOTP)){
            User user=new User(name,email);
            int response= UserService.saveUser(user);
            switch (response){
                case 0-> System.out.println("user already exists");
                case 1-> System.out.println("user registered");
            }
        }
    }
}
