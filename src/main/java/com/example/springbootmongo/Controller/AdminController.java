package com.example.springbootmongo.Controller;

import com.example.springbootmongo.Model.Admin;
import com.example.springbootmongo.Service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // creating an admin
    @PostMapping(value = "/add-admin")
    public String Signup(@RequestBody Admin admin) {
        return adminService.signup(admin);
    }

    //login admin
    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String,Object> map) {
        return adminService.login(map.get("email").toString(), map.get("password").toString());
        // alternately we can do adminService.login(user.getEmail, user.getPassword), args
        // would be @RequestBody Admin user
    }

    //get admin
    @GetMapping(value = "/get-admin")
    public Admin getAdmin(HttpServletRequest request) {
        ObjectId userId = (ObjectId) request.getAttribute("userId");
        return adminService.getAdmin(userId);
    }
}
