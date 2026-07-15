package com.example.du_an_java1.Tú.Repository;

import com.example.du_an_java1.Tú.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private static final List<Account> accounts = new ArrayList<>();

    static {
        accounts.add(new Account("TK001", "Admin System", "admin", "Quản trị viên", "0000000000", "Hoạt động"));
        accounts.add(new Account("TK002", "Nguyễn Văn A", "nv1", "Nhân viên bán hàng", "0901234567", "Hoạt động"));
        accounts.add(new Account("TK003", "Trần Thị B", "nv2", "Nhân viên kho", "0912345678", "Hoạt động"));
        accounts.add(new Account("TK004", "Lê Văn C", "nv3", "Kế toán", "0923456789", "Không hoạt động"));
    }

    public List<Account> getAll() {
        return accounts;
    }

    public void add(Account account) {
        accounts.add(account);
    }

    public Account findByMa(String ma) {
        for (Account a : accounts) {
            if (a.getMa().equalsIgnoreCase(ma)) {
                return a;
            }
        }
        return null;
    }

    public void update(Account account) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getMa().equalsIgnoreCase(account.getMa())) {
                accounts.set(i, account);
                return;
            }
        }
    }

    public void delete(String ma) {
        accounts.removeIf(a -> a.getMa().equalsIgnoreCase(ma));
    }

    public List<Account> search(String keyword) {
        List<Account> result = new ArrayList<>();
        String key = keyword.toLowerCase();

        for (Account a : accounts) {
            if (a.getMa().toLowerCase().contains(key)
                    || a.getHoTen().toLowerCase().contains(key)
                    || a.getUsername().toLowerCase().contains(key)) {
                result.add(a);
            }
        }

        return result;
    }
}