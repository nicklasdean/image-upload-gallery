package com.example.demo.repositories;


import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.ImageBlob;

public class ImageRepository {

    public List<ImageBlob> getAllImages(){
        List<ImageBlob> list = new ArrayList<ImageBlob>();
        try {
            PreparedStatement ps = establishConnection().prepareStatement("select * from img");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ImageBlob tmp = new ImageBlob(rs.getString(1),rs.getBytes(1));
                list.add(tmp);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void uploadImageToDatabase(MultipartFile file) {
        try {
            byte[] fileAsBytes = file.getBytes();
            Blob fileAsBlob = new SerialBlob(fileAsBytes);
            PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO my_company.img VALUES(?,?)");
            ps.setBlob(1,fileAsBlob);
            ps.setString(2,"Description");
            System.out.println(ps.execute());
        }
        catch(SQLException e){
            System.out.println("bad connection");
            e.printStackTrace();
            System.out.println(e.getSQLState());
        }
        catch(IOException e){
            System.out.println("File not found");
        }
    }

    public void uploadAsPath(){

    }

    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_company","dean","securePassword");
        return conn;
    }

}
