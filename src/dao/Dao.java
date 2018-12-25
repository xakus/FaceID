package dao;

import utility.ConsAndStatic;
import utility.DBC;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Dao {

    public void setNumberAndNameToDB(){
        try {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            DBC.Connection();
            DBC.WriteDB("DELETE FROM IMAGE_NAME");
            DBC.WriteDB("INSERT INTO IMAGE_NAME (IMAGE_NUM,NAME,CREATE_D) VALUES ('-1','------------','"+dateFormat.format(date)+"')");
            for(int i=0;i< ConsAndStatic.getName.size();i++){
                String  name= ConsAndStatic.getName.get(i);
                System.out.println("Num="+i+" Name="+name);
                DBC.WriteDB("INSERT INTO IMAGE_NAME (IMAGE_NUM,NAME,CREATE_D) VALUES ('"+i+"','"+name+"','"+dateFormat.format(date)+"')");
            }
            DBC.CloseDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getNumberAndNameFromDB(){
        try {
           ConsAndStatic.getName.clear();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            DBC.Connection();
            ResultSet resultSet=DBC.ReadDB("SELECT * FROM IMAGE_NAME WHERE ACTIVE=1");
            while (resultSet.next()){
                ConsAndStatic.getName.put(resultSet.getInt("IMAGE_NUM"),resultSet.getString("NAME"));
            }
            DBC.CloseDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
