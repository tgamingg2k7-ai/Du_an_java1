package com.example.du_an_java1.model;

import java.math.BigDecimal;

public class CartItem {

    private Product product;
    private int soLuong;

    public CartItem() {
    }

    public CartItem(Product product, int soLuong) {
        this.product = product;
        this.soLuong = soLuong;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getThanhTien() {
        if (product == null || product.getGiaBan() == null) {
            return BigDecimal.ZERO;
        }

        return product.getGiaBan().multiply(BigDecimal.valueOf(soLuong));
    }
}