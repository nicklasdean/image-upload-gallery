package com.example.demo.models;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageBlob {
    private String description;
    private String imgBase64;
    private byte[] img;

    public ImageBlob(String description, byte[] img) {
        this.description = description;
        this.img = img;
        this.imgBase64 = byteArrayAs64String();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgBase64(){
        return this.imgBase64;
    }

    private String byteArrayAs64String(){
        return Base64.encodeBase64String(this.img);
    }
}

