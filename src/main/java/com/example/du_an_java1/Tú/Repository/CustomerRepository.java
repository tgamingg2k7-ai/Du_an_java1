package com.example.du_an_java1.Tú.Repository;

import com.example.du_an_java1.Tú.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private static final List<Customer> customers = new ArrayList<>();

    static {
        customers.add(new Customer("KH001", "Nguyễn Văn A", "0901234567", "a@gmail.com", "Hà Nội", "01/12/2025"));
        customers.add(new Customer("KH002", "Trần Thị B", "0912345678", "b@gmail.com", "Hồ Chí Minh", "02/12/2025"));
        customers.add(new Customer("KH003", "Lê Văn C", "0923456789", "c@gmail.com", "Đà Nẵng", "03/12/2025"));
        customers.add(new Customer("KH004", "Phạm Thị D", "0934567890", "d@gmail.com", "Hải Phòng", "04/12/2025"));
    }

    public List<Customer> getAll() {
        return customers;
    }

    public void add(Customer customer) {
        customers.add(customer);
    }

    public Customer findByMa(String ma) {
        for (Customer c : customers) {
            if (c.getMa().equalsIgnoreCase(ma)) {
                return c;
            }
        }
        return null;
    }

    public void update(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getMa().equalsIgnoreCase(customer.getMa())) {
                customers.set(i, customer);
                return;
            }
        }
    }

    public void delete(String ma) {
        customers.removeIf(c -> c.getMa().equalsIgnoreCase(ma));
    }

    public List<Customer> search(String keyword) {
        List<Customer> result = new ArrayList<>();
        String key = keyword.toLowerCase();

        for (Customer c : customers) {
            if (c.getMa().toLowerCase().contains(key)
                    || c.getHoTen().toLowerCase().contains(key)
                    || c.getSdt().toLowerCase().contains(key)) {
                result.add(c);
            }
        }

        return result;
    }
}