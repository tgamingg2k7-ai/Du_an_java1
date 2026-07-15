package com.example.du_an_java1.Tú.Repository;

import com.example.du_an_java1.Tú.model.AttributeItem;

import java.util.ArrayList;
import java.util.List;

public class AttributeRepository {

    private static final List<AttributeItem> attributes = new ArrayList<>();

    static {
        attributes.add(new AttributeItem("M01", "Đỏ", "#FF0000", "color", "Hoạt động"));
        attributes.add(new AttributeItem("M02", "Xanh dương", "#0000FF", "color", "Hoạt động"));
        attributes.add(new AttributeItem("M03", "Đen", "#000000", "color", "Hoạt động"));

        attributes.add(new AttributeItem("S01", "S", "Size S", "size", "Hoạt động"));
        attributes.add(new AttributeItem("S02", "M", "Size M", "size", "Hoạt động"));
        attributes.add(new AttributeItem("S03", "L", "Size L", "size", "Hoạt động"));

        attributes.add(new AttributeItem("DM01", "Áo", "Danh mục áo", "category", "Hoạt động"));
        attributes.add(new AttributeItem("DM02", "Quần", "Danh mục quần", "category", "Hoạt động"));

        attributes.add(new AttributeItem("TH01", "Nike", "Nike", "brand", "Hoạt động"));
        attributes.add(new AttributeItem("TH02", "Adidas", "Adidas", "brand", "Hoạt động"));
        attributes.add(new AttributeItem("TH03", "Levi's", "Levi's", "brand", "Hoạt động"));
    }

    public List<AttributeItem> getByLoai(String loai) {
        List<AttributeItem> result = new ArrayList<>();

        for (AttributeItem a : attributes) {
            if (a.getLoai().equalsIgnoreCase(loai)) {
                result.add(a);
            }
        }

        return result;
    }

    public void add(AttributeItem item) {
        attributes.add(item);
    }

    public AttributeItem findByMa(String ma) {
        for (AttributeItem a : attributes) {
            if (a.getMa().equalsIgnoreCase(ma)) {
                return a;
            }
        }
        return null;
    }

    public void update(AttributeItem item) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).getMa().equalsIgnoreCase(item.getMa())) {
                attributes.set(i, item);
                return;
            }
        }
    }

    public void delete(String ma) {
        attributes.removeIf(a -> a.getMa().equalsIgnoreCase(ma));
    }
}