package com.poly.services.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.poly.dao.AccountDAO;
import com.poly.dto.AccountToUserDetail;
import com.poly.models.Account;
import com.poly.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDAO aDAO;

    @Override
    public Account getAccountAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return aDAO.findById(userDetails.getUsername()).orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new AccountToUserDetail(getByUsername(username));
    }

    public Account getByUsername(String username) {
        return aDAO.findById(username).orElse(null);
    }

    @Override
    public Account update(Account account) {
        if (aDAO.findById(account.getUsername()) != null) {
            aDAO.save(account);
            return account;
        }
        return null;
    }

    @Override
    public void add(Account account) {
        try {
            aDAO.save(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void delete(String username) {
        aDAO.deleteById(username);
    }
}
