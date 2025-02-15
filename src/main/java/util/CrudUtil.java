//package util;
//
//import db.Db_Connection;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class CrudUtil {
//    public static <T>T execute(String sql, Object...args) throws SQLException {
//        PreparedStatement psTm = Db_Connection.getInstance().getConnection().prepareStatement(sql);
//        for (int = i; i < args.length; i++){
//            psTm.setObject((i+1), args[i]);
//        }
//        if (sql.startsWith("SELECT") || sql.startsWith("select")){
//            return (T) psTm.executeQuery();
//        }
//        return (T) (Boolean) (psTm.executeLargeUpdate() > 0);
//    }
//}
