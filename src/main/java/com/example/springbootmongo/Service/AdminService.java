package com.example.springbootmongo.Service;

import com.example.springbootmongo.Model.Admin;
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

    // Get an admin
    public Admin getAdmin(ObjectId id) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        return optionalAdmin.orElseGet(optionalAdmin::get);
    }

    // list admins
    public List<Admin> getAdmins() {
        List<Admin> getAdmins = adminRepo.findAll();
        return getAdmins;
    }

    // Signup an admin
    public String signup(Admin user) {
        Admin savedAdmin = adminRepo.save(user);
        return "{" +
                "\"message\":"+"Successfully created user\",\n"+
                "\"data\":"+savedAdmin+",\n"+
                "}";
    }

    // login an admin
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