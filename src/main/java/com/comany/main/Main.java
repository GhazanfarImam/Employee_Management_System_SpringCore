
package com.comany.main;

import com.comany.config.AppConfig;
import com.comany.entity.Employee;
import com.comany.service.EmployeeService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        try (Scanner scanner = new Scanner(System.in)) {

            EmployeeService employeeService =
                    context.getBean(EmployeeService.class);

            System.out.println("\nSpring Container Started Successfully!");

            boolean running = true;

            while (running) {

                System.out.println("\n==========================================");
                System.out.println("       EMPLOYEE MANAGEMENT SYSTEM");
                System.out.println("==========================================");
                System.out.println("1. Register Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Find Employee By ID");
                System.out.println("4. Update Employee");
                System.out.println("5. Delete Employee");

                System.out.println("7. Exit");
                System.out.println("==========================================");

                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                try {

                    switch (choice) {

                        // 1. REGISTER EMPLOYEE
                        case "1": {

                            System.out.println("\n========== REGISTER EMPLOYEE ==========");

                            System.out.print("Enter Name: ");
                            String name = scanner.nextLine();

                            System.out.print("Enter Email: ");
                            String email = scanner.nextLine();

                            System.out.print("Enter Department: ");
                            String department = scanner.nextLine();

                            System.out.print("Enter Salary: ");
                            BigDecimal salary =
                                    new BigDecimal(scanner.nextLine());

                            Employee employee = new Employee(
                                    null,
                                    name,
                                    email,
                                    department,
                                    salary
                            );

                            Employee savedEmployee =
                                    employeeService.registerEmployee(employee);

                            System.out.println("\nEmployee Registered Successfully!");
                            System.out.println(savedEmployee);

                            break;
                        }

                        // 2. VIEW ALL EMPLOYEES
                        case "2": {

                            System.out.println("\n========== ALL EMPLOYEES ==========");

                            List<Employee> employees =
                                    employeeService.getAllEmployees();

                            if (employees.isEmpty()) {

                                System.out.println("No Employees Found!");

                            } else {

                                for (Employee employee : employees) {
                                    System.out.println(employee);
                                }

                                System.out.println(
                                        "\nTotal Employees: " + employees.size()
                                );
                            }

                            break;
                        }

                        // 3. FIND EMPLOYEE BY ID
                        case "3": {

                            System.out.println("\n========== FIND EMPLOYEE ==========");

                            System.out.print("Enter Employee ID: ");

                            Long employeeId =
                                    Long.parseLong(scanner.nextLine());

                            Optional<Employee> employee =
                                    employeeService.getEmployeeById(employeeId);

                            if (employee.isPresent()) {

                                System.out.println("\nEmployee Found!");
                                System.out.println(employee.get());

                            } else {

                                System.out.println("Employee Not Found!");
                            }

                            break;
                        }

                        // 4. UPDATE EMPLOYEE
                        case "4": {

                            System.out.println("\n========== UPDATE EMPLOYEE ==========");

                            System.out.print("Enter Employee ID: ");

                            Long employeeId =
                                    Long.parseLong(scanner.nextLine());

                            Optional<Employee> existingEmployee =
                                    employeeService.getEmployeeById(employeeId);

                            if (!existingEmployee.isPresent()) {

                                System.out.println("Employee Not Found!");
                                break;
                            }

                            Employee employee = existingEmployee.get();

                            System.out.println("\nCurrent Employee Details:");
                            System.out.println(employee);

                            System.out.print("Enter New Name: ");
                            String name = scanner.nextLine();

                            System.out.print("Enter New Email: ");
                            String email = scanner.nextLine();

                            System.out.print("Enter New Department: ");
                            String department = scanner.nextLine();

                            System.out.print("Enter New Salary: ");
                            BigDecimal salary =
                                    new BigDecimal(scanner.nextLine());

                            Employee updatedEmployee =
                                    employeeService.updateEmployee(
                                            employeeId,
                                            name,
                                            email,
                                            department,
                                            salary
                                    );

                            System.out.println("\nEmployee Updated Successfully!");
                            System.out.println(updatedEmployee);

                            break;
                        }

                        // 5. DELETE EMPLOYEE
                        case "5": {

                            System.out.println("\n========== DELETE EMPLOYEE ==========");

                            System.out.print("Enter Employee ID: ");

                            Long employeeId =
                                    Long.parseLong(scanner.nextLine());

                            Optional<Employee> employee =
                                    employeeService.getEmployeeById(employeeId);

                            if (!employee.isPresent()) {

                                System.out.println("Employee Not Found!");

                            } else {

                                System.out.println("Employee Details:");
                                System.out.println(employee.get());

                                System.out.print(
                                        "Are you sure you want to delete? (yes/no): "
                                );

                                String confirmation =
                                        scanner.nextLine().trim();

                                if (confirmation.equalsIgnoreCase("yes")) {

                                    employeeService.deleteEmployee(employeeId);

                                    System.out.println(
                                            "Employee Deleted Successfully!"
                                    );

                                } else {

                                    System.out.println("Deletion Cancelled.");
                                }
                            }

                            break;
                        }


                        // 7. EXIT
                        case "7": {

                            System.out.println(
                                    "\nThank you for using Employee Management System!"
                            );

                            running = false;

                            break;
                        }

                        default:

                            System.out.println(
                                    "Invalid choice! Please select between 1 and 7."
                            );
                    }

                } catch (NumberFormatException exception) {

                    System.out.println(
                            "Invalid input! Please enter a valid number."
                    );

                } catch (IllegalArgumentException exception) {

                    System.out.println(
                            "Error: " + exception.getMessage()
                    );

                } catch (Exception exception) {

                    System.out.println(
                            "Something went wrong: " + exception.getMessage()
                    );
                }
            }

        } finally {

            context.close();

            System.out.println("\nSpring Container Closed Successfully!");
        }
    }
}
