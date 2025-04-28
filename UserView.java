package views;

import DAO.DataDAO;
import model.data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email=email;
    }
    public  void Home(){
        do {
            System.out.println("welcome"+this.email);
            System.out.println("press 1 to show hidden files");
            System.out.println("press 2 to hide a new file");
            System.out.println("press 3 to unhide a file");
            System.out.println("press 0 to exit");
            Scanner ss=new Scanner(System.in);
            int ch=Integer.parseInt(ss.nextLine());
            switch (ch){
                case 1->{
                    try {
                        List<data> files= DataDAO.getallfiles(this.email);
                        System.out.println("ID - FileName");
                        for (data file:files){
                            System.out.println(file.getId() +"-"+ file.getFilename());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2->{

                    System.out.println("enter the file path");
                    String path=ss.nextLine();
                    File f=new File(path);
                    data file=new data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hidefile(file);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }case 3->{
                    List<data> files= null;
                    try {
                        files = DataDAO.getallfiles(this.email);

                    System.out.println("ID - FileName");
                    for (data file:files){
                        System.out.println(file.getId() +"-"+ file.getFilename());
                    }
                    System.out.println("enter the id of file to unhide");
                    int id=Integer.parseInt(ss.nextLine());
                    boolean isValid=false;
                    for (data file:files){
                        if (file.getId()==id){
                            isValid=true;
                            break;
                        }
                    }
                    if (isValid){
                        DataDAO.unhide(id);
                    }else {
                        System.out.println("you have enter wrong id");
                    }
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }

                }
                case 0->{
                    System.exit(0);
                }
            }

        }while (true);
    }
}
