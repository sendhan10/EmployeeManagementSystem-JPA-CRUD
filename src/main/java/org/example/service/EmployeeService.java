package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.entity.Employee;
import org.example.util.JPAUtil;

import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    private final EmployeeDAO dao = new EmployeeDAO();

    public void startCLI() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> createEmployee(sc);
                case "2" -> listAll();
                case "3" -> fetchById(sc);
                case "4" -> fetchByName(sc);
                case "5" -> fetchByDept(sc);
                case "6" -> fetchBySalaryRange(sc);
                case "7" -> updateEmployee(sc);
                case "8" -> deleteById(sc);
                case "9" -> countEmployees();
                case "0" -> {
                    running = false;
                    JPAUtil.shutdown();
                    System.out.println("Exiting. Goodbye!");
                }
                default -> System.out.println("Invalid choice.");
            }
            System.out.println();
        }
        sc.close();
    }

    private void printMenu() {
        System.out.println("==== Employee Management ====");
        System.out.println("1) Create employee");
        System.out.println("2) List all employees");
        System.out.println("3) Fetch by ID");
        System.out.println("4) Fetch by Name");
        System.out.println("5) Fetch by Department");
        System.out.println("6) Fetch by Salary Range");
        System.out.println("7) Update employee");
        System.out.println("8) Delete by ID");
        System.out.println("9) Count employees");
        System.out.println("0) Exit");
    }

    private void createEmployee(Scanner sc) {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Department: ");
        String dept = sc.nextLine().trim();
        System.out.print("Salary: ");
        int salary = Integer.parseInt(sc.nextLine().trim());

        Employee e = new Employee(name, dept, salary);
        dao.save(e);
        System.out.println("Saved: " + e);
    }

    private void listAll() {
        List<Employee> all = dao.findAll();
        if (all.isEmpty()) System.out.println("No employees found.");
        else all.forEach(System.out::println);
    }

    private void fetchById(Scanner sc) {
        System.out.print("ID: ");
        Long id = Long.parseLong(sc.nextLine().trim());
        Employee e = dao.findById(id);
        System.out.println(e != null ? e : "Not found");
    }

    private void fetchByName(Scanner sc) {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        List<Employee> results = dao.findByName(name);
        if (results.isEmpty()) System.out.println("No matches.");
        else results.forEach(System.out::println);
    }

    private void fetchByDept(Scanner sc) {
        System.out.print("Department: ");
        String dept = sc.nextLine().trim();
        List<Employee> results = dao.findByDepartment(dept);
        if (results.isEmpty()) System.out.println("No matches.");
        else results.forEach(System.out::println);
    }

    private void fetchBySalaryRange(Scanner sc) {
        System.out.print("Min salary: ");
        int min = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Max salary: ");
        int max = Integer.parseInt(sc.nextLine().trim());
        List<Employee> results = dao.findBySalaryRange(min, max);
        if (results.isEmpty()) System.out.println("No matches.");
        else results.forEach(System.out::println);
    }

    private void updateEmployee(Scanner sc) {
        System.out.print("ID to update: ");
        Long id = Long.parseLong(sc.nextLine().trim());
        Employee existing = dao.findById(id);
        if (existing == null) {
            System.out.println("Employee not found.");
            return;
        }
        System.out.println("Current: " + existing);
        System.out.print("New name (leave blank to keep): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) existing.setName(name);
        System.out.print("New department (leave blank to keep): ");
        String dept = sc.nextLine().trim();
        if (!dept.isEmpty()) existing.setDepartment(dept);
        System.out.print("New salary (leave blank to keep): ");
        String sal = sc.nextLine().trim();
        if (!sal.isEmpty()) existing.setSalary(Integer.parseInt(sal));

        dao.update(existing);
        System.out.println("Updated: " + existing);
    }

    private void deleteById(Scanner sc) {
        System.out.print("ID to delete: ");
        Long id = Long.parseLong(sc.nextLine().trim());
        boolean ok = dao.deleteById(id);
        System.out.println(ok ? "Deleted." : "Not found.");
    }

    private void countEmployees() {
        System.out.println("Total employees: " + dao.count());
    }
}
