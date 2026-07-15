package com.example.du_an_java1.Tú.model;

public class AttributeItem {

    private String ma;
    private String ten;
    private String giaTri;
    private String loai;
    private String trangThai;

    public AttributeItem() {
    }

    public AttributeItem(String ma, String ten, String giaTri, String loai, String trangThai) {
        this.ma = ma;
        this.ten = ten;
        this.giaTri = giaTri;
        this.loai = loai;
        this.trangThai = trangThai;
    }

    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getGiaTri() { return giaTri; }
    public void setGiaTri(String giaTri) { this.giaTri = giaTri; }

    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}