package com.example.du_an_java1.model;

public class Account {

    private String ma;
    private String hoTen;
    private String username;
    private String vaiTro;
    private String sdt;
    private String trangThai;

    public Account() {
    }

    public Account(String ma, String hoTen, String username, String vaiTro, String sdt, String trangThai) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.username = username;
        this.vaiTro = vaiTro;
        this.sdt = sdt;
        this.trangThai = trangThai;
    }

    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}