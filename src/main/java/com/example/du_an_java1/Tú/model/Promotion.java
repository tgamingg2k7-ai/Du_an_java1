package com.example.du_an_java1.Tú.model;

public class Promotion {

    private String ma;
    private String ten;
    private String loaiGiam;
    private String giaTri;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String trangThai;

    public Promotion() {
    }

    public Promotion(String ma, String ten, String loaiGiam, String giaTri,
                     String ngayBatDau, String ngayKetThuc, String trangThai) {
        this.ma = ma;
        this.ten = ten;
        this.loaiGiam = loaiGiam;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getLoaiGiam() { return loaiGiam; }
    public void setLoaiGiam(String loaiGiam) { this.loaiGiam = loaiGiam; }

    public String getGiaTri() { return giaTri; }
    public void setGiaTri(String giaTri) { this.giaTri = giaTri; }

    public String getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(String ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public String getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(String ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}