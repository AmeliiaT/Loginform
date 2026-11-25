package loginform;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.*;
import java.security.MessageDigest;

public class mainfile {
    static String FILE = "password.txt";
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hash = md.digest(password.getBytes("UTF-8"));

        StringBuilder result = new StringBuilder();

        for (byte b : hash) {
            result.append(String.format("%02x", b & 0xff));
        }

        return result.toString();
    }
    public static void savePassword(Scanner scanner) throws Exception {
        System.out.print("Створіть пароль: ");
        String password = scanner.nextLine();

        String hashed = hashPassword(password);

        try (FileWriter writer = new FileWriter(FILE)) {
            writer.write(hashed);
        }

        System.out.println("Пароль успішно збережено!");
    }

    // Читання та перевірка пароля
    public static void checkPassword(Scanner scanner) throws Exception {
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        String savedHash;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE))) {
            savedHash = reader.readLine();
        }

        if (hashPassword(password).equals(savedHash)) {
            System.out.println("Доступ дозволено!");
        } else {
            System.out.println("Неправильний пароль!");
        }
    }
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Path users = Path.of("users.txt");
        if(Files.exists(users)){
            
        
        int q = 0;
        String login = "";
        String email = "";
        String password = "";

        while (q == 0) {
            System.out.println("1. Registration");
            System.out.println("2. Login");
            System.out.println("0. Exit program");
            String text = Files.readString(users);
            System.out.println("Choose an action by entering the corresponding number");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                int a = 0;
                for (int i = 0; i < 10; i++) {
                    System.out.println("");
                }
                while (a == 0) {
                    System.out.println("Enter your username");
                    login = scanner.nextLine();
                    if (login.matches("[a-zA-Z0-9_]+") && login.length() >= 4 && login.length() <= 20) {
                        a = 1;
                        while (a == 1) {
                            for (int i = 0; i < 10; i++) {
                                System.out.println("");
                            }
                            System.out.println("Enter your email");
                            email = scanner.nextLine();
                            if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") && !email.matches("[ ]+")) {
                                a = 2;
                                for (int i = 0; i < 10; i++) {
                                    System.out.println("");
                                }
                                while (a == 2) {
                                    savePassword(scanner);
                                    a = 3;
                                }
                                while (a == 3) {
                                    System.out.println("Confirm your password");
                                    String confirmPassword = scanner.nextLine();
                                    if (confirmPassword.equals(password)) {
                                        a = 4;
                                                Files.write(users, new byte[0]);
                                                Files.writeString(users, "Username: " + login);
                                                Files.writeString(users, "\nEmail: " + email, StandardOpenOption.APPEND);
                                                Files.writeString(users, "\nPassword: " + password, StandardOpenOption.APPEND);
                                            } else {
                                                System.out.println("Password confirmation failed");
                                            }
                                        }
                                
                                
                            } else {
                                System.out.println("Invalid email");
                            }
                        }
                    } else {
                        System.out.println("Your username is invalid!");
                        a = 0;
                    }
                }
            } else if (choice == 2) {
                int b = 0;
                while (b == 0) {
                    System.out.println("Enter your username");
                    String checkLogin = scanner.nextLine();
                    if (checkLogin.equals(login)) {
                        b = 1;
                        System.out.println("Your username is correct");
                        System.out.println("Enter your email");
                        String checkEmail = scanner.nextLine();
                        if (checkEmail.equals(email)) {
                            b = 2;
                            System.out.println("email is correct");
                            System.out.println("Enter your password");
                            checkPassword(scanner);
                        } else {
                            System.out.println("Your email doesn`t exist");
                        }
                    } else {
                        System.out.println("Your username doesn`t exist!");
                    }
                }
            } else if (choice == 0) {
                System.out.println("Program terminated");
                q = 1;
            } else {
                System.out.println("Error. This action does not exist");
            }
        }
        scanner.close();
    } 
}

public static void main2(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        File file = new File(FILE);

        if (!file.exists()) {
            savePassword(scanner);
        } else {
            checkPassword(scanner);
        }

        scanner.close();
    }
    }

