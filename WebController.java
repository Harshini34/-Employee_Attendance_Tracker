package com.example.attendance.controller;

import com.example.attendance.model.Attendance;
import com.example.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/employee/mark")
    public String markPresent(Authentication auth) {
        // Logic to mark attendance (stub)
        return "employee-dashboard";
    }

    @GetMapping("/employee/dashboard")
    public String employeeDashboard() {
        return "employee-dashboard";
    }

    @GetMapping("/manager/dashboard")
    public String managerDashboard() {
        return "manager-dashboard";
    }

    @GetMapping("/manager/report")
    public String viewReport(Model model) {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        model.addAttribute("attendanceList", attendanceList);
        return "report";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
            return "redirect:/manager/dashboard";
        } else {
            return "redirect:/employee/dashboard";
        }
    }
}