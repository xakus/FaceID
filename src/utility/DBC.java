package utility;

import java.sql.*;

public class DBC {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Connection() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:db.db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
//    public static void CreateDB() throws ClassNotFoundException, SQLException
//    {
//        statmt = conn.createStatement();
//        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");
//
//        System.out.println("Таблица создана или уже существует.");
//    }

    // --------Заполнение таблицы--------
    public static void WriteDB(String sql) throws SQLException {
        statmt=conn.createStatement();
        statmt.execute(sql);
        //statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
        //statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static ResultSet ReadDB(String sql) throws ClassNotFoundException, SQLException {
        statmt=conn.createStatement();
        resSet = statmt.executeQuery(sql);

//        while(resSet.next())
//        {
//            int id = resSet.getInt("id");
//            String  name = resSet.getString("name");
//            String  phone = resSet.getString("phone");
//            System.out.println( "ID = " + id );
//            System.out.println( "name = " + name );
//            System.out.println( "phone = " + phone );
//            System.out.println();
//        }
        System.out.println("Таблица выведена");
        return resSet;

    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException {
        if (conn!=null){
        conn.close();}
        if (statmt!=null){
        statmt.close();}
        if (resSet!=null){
        resSet.close();}

        System.out.println("Соединения закрыты");
    }

}
