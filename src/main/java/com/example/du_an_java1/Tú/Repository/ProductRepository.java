package com.example.du_an_java1.Tú.Repository;

import com.example.du_an_java1.config.DBConnect;
import com.example.du_an_java1.Tú.model.Product;

import java.sql.*;
import java.util.*;

public class ProductRepository {

    public List<Product> getAll() {
        return search(null, null, null);
    }

    public List<Product> search(String keyword, String category, String status) {
        List<Product> list = new ArrayList<>();

        String sql = """
                SELECT 
                    MaSP,
                    MaSanPham,
                    TenSP,
                    TenDanhMuc,
                    TenThuongHieu,
                    GiaBan,
                    ChatLieu,
                    GioiTinh,
                    TrangThai,
                    TongSoLuong
                FROM v_DanhSachSanPham
                WHERE
                    (? IS NULL OR MaSanPham LIKE ? OR TenSP LIKE ?)
                    AND (? IS NULL OR TenDanhMuc = ?)
                    AND (? IS NULL OR TrangThai = ?)
                ORDER BY MaSP DESC
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            String kw = null;
            if (keyword != null && !keyword.trim().isEmpty()) {
                kw = "%" + keyword.trim() + "%";
            }

            String cate = null;
            if (category != null && !category.trim().isEmpty()) {
                cate = category.trim();
            }

            Integer stt = null;
            if (status != null && !status.trim().isEmpty()) {
                stt = Integer.parseInt(status);
            }

            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);

            ps.setString(4, cate);
            ps.setString(5, cate);

            if (stt == null) {
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(6, stt);
                ps.setInt(7, stt);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();

                p.setMaSP(rs.getInt("MaSP"));
                p.setMaSanPham(rs.getString("MaSanPham"));
                p.setTenSP(rs.getString("TenSP"));
                p.setTenDanhMuc(rs.getString("TenDanhMuc"));
                p.setTenThuongHieu(rs.getString("TenThuongHieu"));
                p.setGiaBan(rs.getBigDecimal("GiaBan"));
                p.setChatLieu(rs.getString("ChatLieu"));
                p.setGioiTinh(rs.getString("GioiTinh"));
                p.setTrangThai(rs.getBoolean("TrangThai"));
                p.setTongSoLuong(rs.getInt("TongSoLuong"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Product findById(int id) {
        String sql = """
                SELECT 
                    sp.MaSP,
                    sp.MaDM,
                    sp.MaTH,
                    sp.MaNCC,
                    sp.MaSanPham,
                    sp.TenSP,
                    sp.GiaBan,
                    sp.MoTa,
                    sp.ChatLieu,
                    sp.GioiTinh,
                    sp.TrangThai,
                    dm.TenDanhMuc,
                    th.TenThuongHieu,
                    ncc.TenNCC,
                    ISNULL(SUM(bt.SoLuong), 0) AS TongSoLuong
                FROM SanPham sp
                JOIN DanhMuc dm ON sp.MaDM = dm.MaDM
                JOIN ThuongHieu th ON sp.MaTH = th.MaTH
                LEFT JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC
                LEFT JOIN BienTheSanPham bt ON sp.MaSP = bt.MaSP
                WHERE sp.MaSP = ?
                GROUP BY 
                    sp.MaSP,
                    sp.MaDM,
                    sp.MaTH,
                    sp.MaNCC,
                    sp.MaSanPham,
                    sp.TenSP,
                    sp.GiaBan,
                    sp.MoTa,
                    sp.ChatLieu,
                    sp.GioiTinh,
                    sp.TrangThai,
                    dm.TenDanhMuc,
                    th.TenThuongHieu,
                    ncc.TenNCC
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Product p = new Product();

                p.setMaSP(rs.getInt("MaSP"));
                p.setMaDM(rs.getInt("MaDM"));
                p.setMaTH(rs.getInt("MaTH"));

                int maNCC = rs.getInt("MaNCC");
                if (rs.wasNull()) {
                    p.setMaNCC(null);
                } else {
                    p.setMaNCC(maNCC);
                }

                p.setMaSanPham(rs.getString("MaSanPham"));
                p.setTenSP(rs.getString("TenSP"));
                p.setGiaBan(rs.getBigDecimal("GiaBan"));
                p.setMoTa(rs.getString("MoTa"));
                p.setChatLieu(rs.getString("ChatLieu"));
                p.setGioiTinh(rs.getString("GioiTinh"));
                p.setTrangThai(rs.getBoolean("TrangThai"));
                p.setTenDanhMuc(rs.getString("TenDanhMuc"));
                p.setTenThuongHieu(rs.getString("TenThuongHieu"));
                p.setTenNCC(rs.getString("TenNCC"));
                p.setTongSoLuong(rs.getInt("TongSoLuong"));

                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void add(Product p) {
        String sql = """
                INSERT INTO SanPham
                (
                    MaDM,
                    MaTH,
                    MaNCC,
                    MaSanPham,
                    TenSP,
                    GiaBan,
                    MoTa,
                    ChatLieu,
                    GioiTinh,
                    TrangThai
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, p.getMaDM());
            ps.setInt(2, p.getMaTH());

            if (p.getMaNCC() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, p.getMaNCC());
            }

            ps.setString(4, p.getMaSanPham());
            ps.setString(5, p.getTenSP());
            ps.setBigDecimal(6, p.getGiaBan());
            ps.setString(7, p.getMoTa());
            ps.setString(8, p.getChatLieu());
            ps.setString(9, p.getGioiTinh());
            ps.setBoolean(10, p.isTrangThai());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Product p) {
        String sql = """
                UPDATE SanPham
                SET 
                    MaDM = ?,
                    MaTH = ?,
                    MaNCC = ?,
                    MaSanPham = ?,
                    TenSP = ?,
                    GiaBan = ?,
                    MoTa = ?,
                    ChatLieu = ?,
                    GioiTinh = ?,
                    TrangThai = ?
                WHERE MaSP = ?
                """;

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, p.getMaDM());
            ps.setInt(2, p.getMaTH());

            if (p.getMaNCC() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, p.getMaNCC());
            }

            ps.setString(4, p.getMaSanPham());
            ps.setString(5, p.getTenSP());
            ps.setBigDecimal(6, p.getGiaBan());
            ps.setString(7, p.getMoTa());
            ps.setString(8, p.getChatLieu());
            ps.setString(9, p.getGioiTinh());
            ps.setBoolean(10, p.isTrangThai());
            ps.setInt(11, p.getMaSP());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = """
                UPDATE SanPham
                SET TrangThai = 0
                WHERE MaSP = ?
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

    public List<Map<String, Object>> getAllDanhMuc() {
        String sql = """
                SELECT MaDM, TenDanhMuc
                FROM DanhMuc
                WHERE TrangThai = 1
                ORDER BY MaDM
                """;

        return getOptions(sql);
    }

    public List<Map<String, Object>> getAllThuongHieu() {
        String sql = """
                SELECT MaTH, TenThuongHieu
                FROM ThuongHieu
                WHERE TrangThai = 1
                ORDER BY MaTH
                """;

        return getOptions(sql);
    }

    public List<Map<String, Object>> getAllNhaCungCap() {
        String sql = """
                SELECT MaNCC, TenNCC
                FROM NhaCungCap
                WHERE TrangThai = 1
                ORDER BY MaNCC
                """;

        return getOptions(sql);
    }

    private List<Map<String, Object>> getOptions(String sql) {
        List<Map<String, Object>> list = new ArrayList<>();

        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    map.put(metaData.getColumnLabel(i), rs.getObject(i));
                }

                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Product product) {

        String sql = """
            INSERT INTO SanPham
            (
                MaDM,
                MaTH,
                MaNCC,
                MaSanPham,
                TenSP,
                GiaBan,
                MoTa,
                ChatLieu,
                GioiTinh,
                TrangThai
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, product.getMaDM());
            statement.setInt(2, product.getMaTH());

            if (product.getMaNCC() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, product.getMaNCC());
            }

            statement.setString(4, product.getMaSanPham());
            statement.setString(5, product.getTenSP());
            statement.setBigDecimal(6, product.getGiaBan());
            statement.setString(7, product.getMoTa());
            statement.setString(8, product.getChatLieu());
            statement.setString(9, product.getGioiTinh());
            statement.setBoolean(10, product.isTrangThai());

            int result = statement.executeUpdate();

            if (result == 0) {
                throw new SQLException("Không thể thêm sản phẩm.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Lỗi thêm sản phẩm: " + e.getMessage(),
                    e
            );
        }
    }
}
