package com.example.du_an_java1.Repository;

import com.example.du_an_java1.config.DBConnect;
import com.example.du_an_java1.model.CartItem;
import com.example.du_an_java1.model.Invoice;
import com.example.du_an_java1.model.InvoiceDetail;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {

    public List<Invoice> getAll() {
        return search(null);
    }

    public List<Invoice> search(String keyword) {
        List<Invoice> list = new ArrayList<>();

        String sql = """
                SELECT 
                    hd.MaHD,
                    hd.MaHoaDon,
                    hd.NgayTao,
                    ISNULL(kh.HoTen, N'Khách lẻ') AS TenKhachHang,
                    tk.HoTen AS TenNhanVien,
                    hd.TongTienHang,
                    hd.TienGiam,
                    hd.TongThanhToan,
                    hd.HinhThucThanhToan,
                    hd.TrangThai,
                    hd.GhiChu
                FROM HoaDon hd
                LEFT JOIN KhachHang kh ON hd.MaKH = kh.MaKH
                JOIN TaiKhoan tk ON hd.MaTK = tk.MaTK
                WHERE 
                    (? IS NULL 
                    OR hd.MaHoaDon LIKE ?
                    OR kh.HoTen LIKE ?
                    OR tk.HoTen LIKE ?
                    OR hd.TrangThai LIKE ?)
                ORDER BY hd.MaHD DESC
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            String kw = null;

            if (keyword != null && !keyword.trim().isEmpty()) {
                kw = "%" + keyword.trim() + "%";
            }

            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ps.setString(5, kw);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapInvoice(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Invoice findById(int id) {
        String sql = """
                SELECT 
                    hd.MaHD,
                    hd.MaHoaDon,
                    hd.NgayTao,
                    ISNULL(kh.HoTen, N'Khách lẻ') AS TenKhachHang,
                    tk.HoTen AS TenNhanVien,
                    hd.TongTienHang,
                    hd.TienGiam,
                    hd.TongThanhToan,
                    hd.HinhThucThanhToan,
                    hd.TrangThai,
                    hd.GhiChu
                FROM HoaDon hd
                LEFT JOIN KhachHang kh ON hd.MaKH = kh.MaKH
                JOIN TaiKhoan tk ON hd.MaTK = tk.MaTK
                WHERE hd.MaHD = ?
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Invoice invoice = mapInvoice(rs);
                invoice.setDetails(getDetails(id));
                return invoice;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<InvoiceDetail> getDetails(int maHD) {
        List<InvoiceDetail> list = new ArrayList<>();

        String sql = """
                SELECT
                    hdct.MaHDCT,
                    hdct.MaHD,
                    sp.TenSP,
                    kc.TenSize,
                    ms.TenMau,
                    hdct.SoLuong,
                    hdct.DonGia,
                    hdct.ThanhTien
                FROM HoaDonChiTiet hdct
                JOIN BienTheSanPham bt ON hdct.MaBienThe = bt.MaBienThe
                JOIN SanPham sp ON bt.MaSP = sp.MaSP
                JOIN KichCo kc ON bt.MaSize = kc.MaSize
                JOIN MauSac ms ON bt.MaMau = ms.MaMau
                WHERE hdct.MaHD = ?
                ORDER BY hdct.MaHDCT
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, maHD);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InvoiceDetail detail = new InvoiceDetail();

                detail.setMaHDCT(rs.getInt("MaHDCT"));
                detail.setMaHD(rs.getInt("MaHD"));
                detail.setTenSP(rs.getString("TenSP"));
                detail.setTenSize(rs.getString("TenSize"));
                detail.setTenMau(rs.getString("TenMau"));
                detail.setSoLuong(rs.getInt("SoLuong"));
                detail.setDonGia(rs.getBigDecimal("DonGia"));
                detail.setThanhTien(rs.getBigDecimal("ThanhTien"));

                list.add(detail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void delete(int id) {
        String sql = """
                UPDATE HoaDon
                SET TrangThai = N'Đã hủy'
                WHERE MaHD = ?
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createFromSale(String khachHang, BigDecimal total, List<CartItem> cart) {
        String maHoaDon = "HD" + System.currentTimeMillis();

        String insertHoaDonSql = """
                INSERT INTO HoaDon
                (
                    MaKH,
                    MaTK,
                    MaPGG,
                    MaHoaDon,
                    TongTienHang,
                    TienGiam,
                    TongThanhToan,
                    HinhThucThanhToan,
                    TrangThai,
                    GhiChu
                )
                VALUES
                (
                    NULL,
                    ?,
                    NULL,
                    ?,
                    ?,
                    0,
                    ?,
                    N'Tiền mặt',
                    N'Đã thanh toán',
                    ?
                )
                """;

        String insertChiTietSql = """
                INSERT INTO HoaDonChiTiet
                (
                    MaHD,
                    MaBienThe,
                    SoLuong,
                    DonGia
                )
                VALUES (?, ?, ?, ?)
                """;

        String updateStockSql = """
                UPDATE BienTheSanPham
                SET SoLuong = SoLuong - ?
                WHERE MaBienThe = ?
                  AND SoLuong >= ?
                """;

        try (Connection conn = DBConnect.getConnection()) {
            if (conn == null) {
                return false;
            }

            conn.setAutoCommit(false);

            try {
                int maTK = getDefaultTaiKhoan(conn);
                int maHD;

                try (PreparedStatement ps = conn.prepareStatement(insertHoaDonSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, maTK);
                    ps.setString(2, maHoaDon);
                    ps.setBigDecimal(3, total);
                    ps.setBigDecimal(4, total);
                    ps.setString(5, "Khách hàng: " + khachHang);

                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();

                    if (rs.next()) {
                        maHD = rs.getInt(1);
                    } else {
                        rollback(conn);
                        return false;
                    }
                }

                for (CartItem item : cart) {
                    if (item == null || item.getProduct() == null) {
                        continue;
                    }

                    int maSP = item.getProduct().getMaSP();
                    int soLuong = item.getSoLuong();
                    BigDecimal donGia = item.getProduct().getGiaBan();

                    int maBienThe = findAvailableVariant(conn, maSP, soLuong);

                    if (maBienThe == -1) {
                        rollback(conn);
                        return false;
                    }

                    try (PreparedStatement ps = conn.prepareStatement(insertChiTietSql)) {
                        ps.setInt(1, maHD);
                        ps.setInt(2, maBienThe);
                        ps.setInt(3, soLuong);
                        ps.setBigDecimal(4, donGia);
                        ps.executeUpdate();
                    }

                    try (PreparedStatement ps = conn.prepareStatement(updateStockSql)) {
                        ps.setInt(1, soLuong);
                        ps.setInt(2, maBienThe);
                        ps.setInt(3, soLuong);

                        int affected = ps.executeUpdate();

                        if (affected == 0) {
                            rollback(conn);
                            return false;
                        }
                    }
                }

                conn.commit();
                return true;

            } catch (Exception e) {
                rollback(conn);
                e.printStackTrace();
                return false;
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createFromSale(String khachHang, double total) {
        String maHoaDon = "HD" + System.currentTimeMillis();

        String sql = """
                INSERT INTO HoaDon
                (
                    MaKH,
                    MaTK,
                    MaPGG,
                    MaHoaDon,
                    TongTienHang,
                    TienGiam,
                    TongThanhToan,
                    HinhThucThanhToan,
                    TrangThai,
                    GhiChu
                )
                VALUES
                (
                    NULL,
                    ?,
                    NULL,
                    ?,
                    ?,
                    0,
                    ?,
                    N'Tiền mặt',
                    N'Đã thanh toán',
                    ?
                )
                """;

        try (Connection conn = DBConnect.getConnection()) {
            if (conn == null) {
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                int maTK = getDefaultTaiKhoan(conn);

                ps.setInt(1, maTK);
                ps.setString(2, maHoaDon);
                ps.setBigDecimal(3, BigDecimal.valueOf(total));
                ps.setBigDecimal(4, BigDecimal.valueOf(total));
                ps.setString(5, "Khách hàng: " + khachHang);

                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Invoice mapInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();

        invoice.setMaHD(rs.getInt("MaHD"));
        invoice.setMaHoaDon(rs.getString("MaHoaDon"));
        invoice.setNgayTao(rs.getTimestamp("NgayTao"));
        invoice.setTenKhachHang(rs.getString("TenKhachHang"));
        invoice.setTenNhanVien(rs.getString("TenNhanVien"));
        invoice.setTongTienHang(rs.getBigDecimal("TongTienHang"));
        invoice.setTienGiam(rs.getBigDecimal("TienGiam"));
        invoice.setTongThanhToan(rs.getBigDecimal("TongThanhToan"));
        invoice.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
        invoice.setTrangThai(rs.getString("TrangThai"));
        invoice.setGhiChu(rs.getString("GhiChu"));

        return invoice;
    }

    private int getDefaultTaiKhoan(Connection conn) throws SQLException {
        String sql = """
                SELECT TOP 1 MaTK
                FROM TaiKhoan
                ORDER BY MaTK
                """;

        try (
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt("MaTK");
            }
        }

        throw new SQLException("Không tìm thấy tài khoản nhân viên.");
    }

    private int findAvailableVariant(Connection conn, int maSP, int soLuong) throws SQLException {
        String sql = """
                SELECT TOP 1 MaBienThe
                FROM BienTheSanPham
                WHERE MaSP = ?
                  AND TrangThai = 1
                  AND SoLuong >= ?
                ORDER BY MaBienThe
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            ps.setInt(2, soLuong);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("MaBienThe");
            }
        }

        return -1;
    }

    private void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}