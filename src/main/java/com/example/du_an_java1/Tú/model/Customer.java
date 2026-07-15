package com.example.du_an_java1.Tú.model;

public class Customer {

    private String ma;
    private String hoTen;
    private String sdt;
    private String email;
    private String diaChi;
    private String ngayTao;

    public Customer() {
    }

    public Customer(String ma, String hoTen, String sdt, String email, String diaChi, String ngayTao) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.ngayTao = ngayTao;
    }

    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }
}