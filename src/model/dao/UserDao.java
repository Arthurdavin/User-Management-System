//package model.dao;
//
//import model.UserDataBase;
//import model.entity.User;
//
//import java.util.List;
//
//public class UserDao {
//
//    private  static final String url = "jdbc:postgresql://localhost:5432/user_db";
//    private  static final String username = "postgres";
//    private  static final String password = "vin1205";
//
//    public List<User> findAll(){
//        return UserDataBase.users;
//    }
//
//    public int remove(User user){
//        boolean removed = UserDataBase.users.remove(user);
//        return removed ? 1:0;
//    }
//
//    public User update(User uu){
//        User user = UserDataBase.users.stream()
//                .filter(u->u.getId().equals(uu.getId()))
//                .findFirst()
//                .orElseThrow(()-> new RuntimeException("User is not found"));
//        // remove old version of User
////        UserDataBase.users.remove(user);
//        //update
//        user.setName(uu.getName());
//        user.setEmail(uu.getEmail());
//        user.setPassword(uu.getPassword());
//        user.setProfile(uu.getProfile());
////        UserDataBase.users.add(user);
//        return user;
//    }
//    public User save(User user){
//        UserDataBase.users.add(user);
//        return user;
//    }
//}


package model.dao;

import model.entity.User;
import util.DataConnectionConfigure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        String sql = """
                SELECT * FROM users;
                """;
        try(Connection conn = DataConnectionConfigure.getConnection()){

            Statement stmt = conn.createStatement();
            boolean isExcuted = stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                int id = rs.getInt("id");
                String uuid = rs.getString("uuid");
                String username = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String profile = rs.getString("profile");

                User user = new User(id,uuid,username,email,password,profile);
                users.add(user);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public User save(User user){
        String sql = """
                INSERT INTO users(uuid,user_name,email,password,profile)
                VALUES(?,?,?,?,?)
                """;

        try(Connection conn = DataConnectionConfigure.getConnection()){
            // prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUuid().toString());
            ps.setString(2,user.getName());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getProfile());

            int rowAffected = ps.executeUpdate();
            if (rowAffected<=0){
                throw new RuntimeException("Save failed");
            }
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public int remove(User user){
        String sql = """
                DELETE FROM users WHERE uuid=?
                """;
        try(Connection conn = DataConnectionConfigure.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUuid());
            return ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public User update(User user){
        String sql = """
                UPDATE users
                SET user_name= ?,email=?,password=?,profile=?
                WHERE uuid=?
                """;
        try(Connection conn = DataConnectionConfigure.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getProfile());
            ps.setString(5, user.getUuid());
            int rowAffected = ps.executeUpdate();
            if (rowAffected<=0){
                throw new RuntimeException("Update failed");
            }
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public List<User> searchByName(String name) {
        List<User> users = new ArrayList<>();

        String sql = """
            SELECT * FROM users
            WHERE user_name ILIKE ?
            """;

        try (Connection conn = DataConnectionConfigure.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("uuid"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
    public User searchByUuid(String uuid){
        String sql = """
                SELECT * FROM users
                WHERE uuid LIKE ?
                """;
        try(Connection conn = DataConnectionConfigure.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new User(
                        rs.getInt("id"),
                        rs.getString("uuid"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}