package com.example.attendance.service;

import com.example.attendance.model.Attendance;
import com.example.attendance.model.Employee;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void markAttendance(String username) {
        Employee employee = employeeRepository.findByName(username);
        if (employee == null) {
            employee = new Employee();
            employee.setName(username);
            employee.setDepartment("IT");
            employee.setDesignation("Developer");
            employee = employeeRepository.save(employee);
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(LocalDate.now());
        attendance.setStatus("Present");

        attendanceRepository.save(attendance);
    }
}