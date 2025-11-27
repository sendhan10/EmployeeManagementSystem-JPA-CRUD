package org.example;

import org.example.service.EmployeeService;

public class App {
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();
        service.startCLI();
    }
}
