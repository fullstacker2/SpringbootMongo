package com.example.springbootmongo.Service;

import com.example.springbootmongo.Model.Admin;
import com.example.springbootmongo.Model.Employee;
import com.example.springbootmongo.Repository.AdminRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private AdminRepo adminRepo;
    private TokenService tokenService;
    @Autowired
    public AdminService(AdminRepo adminRepo, TokenService tokenService) {
        this.adminRepo = adminRepo;
        this.tokenService = tokenService;
    }

    // Signup an admin
    public String signup(Admin admin) {
        Admin savedAdmin = adminRepo.save(admin);
        return "{" +
                "\"message\":"+"Successfully created admin\",\n"+
                "\"data\":"+savedAdmin+",\n"+
                "}";
    }

    // Admin login
    public String login(String email, String password) {
        List<Admin> foundAdmins = adminRepo.getAdminByEmail(email);
        if(foundAdmins.isEmpty()) {
            return "Authentication failed: Admin not found";
        } else if (!foundAdmins.get(0).getPassword().equals(password)) {
            return "Password incorrect";
        }
        return "{\n" +
                "\"message\":"+"\" Successfully Logged-in\",\n"+
                "\"data\": {\n"+" Name : "+foundAdmins.get(0).getName()+",\n"+
                "Email : "+foundAdmins.get(0).getEmail()+"\n"+
                "\"token\":\""+tokenService.createToken(foundAdmins.get(0).getId())+"\"" +
                "}";
    }
}