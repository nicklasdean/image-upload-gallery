package com.example.demo.repositories;


import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.ImageBlob;

public class ImageRepository {
    private final String IMAGE_PATH = "src/main/resources/static/img/uploaded-images";

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
            ps.execute();
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

    public void uploadAsPath(MultipartFile img){
        String fileName = StringUtils.cleanPath(img.getOriginalFilename());
        try {

            PreparedStatement ps = establishConnection().prepareStatement("insert into my_company.imgPath (description, path) VALUES(?,?)");
            ps.setString(1,fileName);
            ps.setString(2, "description");
            ps.execute();
            saveFile(IMAGE_PATH,fileName,img);
        }
        catch(SQLException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    private Connection establishConnection() throws SQLException {
        //Lav en forbindelse
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_company","dean","securePassword");
        return conn;
    }

}
