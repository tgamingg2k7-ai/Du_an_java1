package com.example.du_an_java1.Repository;

import com.example.du_an_java1.model.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {

    private static final List<Promotion> promotions = new ArrayList<>();

    static {
        promotions.add(new Promotion("KM001", "Giảm giá 10%", "Phần trăm", "10%", "01/12/2025", "31/12/2025", "Đang hoạt động"));
        promotions.add(new Promotion("KM002", "Giảm 50k đơn từ 500k", "Giá trị", "50.000đ", "01/12/2025", "31/12/2025", "Đang hoạt động"));
        promotions.add(new Promotion("KM003", "Black Friday", "Phần trăm", "20%", "15/11/2025", "30/11/2025", "Đã kết thúc"));
    }

    public List<Promotion> getAll() {
        return promotions;
    }

    public void add(Promotion promotion) {
        promotions.add(promotion);
    }

    public Promotion findByMa(String ma) {
        for (Promotion p : promotions) {
            if (p.getMa().equalsIgnoreCase(ma)) {
                return p;
            }
        }
        return null;
    }

    public void update(Promotion promotion) {
        for (int i = 0; i < promotions.size(); i++) {
            if (promotions.get(i).getMa().equalsIgnoreCase(promotion.getMa())) {
                promotions.set(i, promotion);
                return;
            }
        }
    }

    public void delete(String ma) {
        promotions.removeIf(p -> p.getMa().equalsIgnoreCase(ma));
    }
}