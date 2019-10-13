import dao.UserDao;
import dao.UserDaoJdbc;
import model.Client;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String ADD_USER = "0";
    private static final String ALL = "1";
    private static final String ADD_CLIENT = "2";
    private static final String EXIT = "3";

    public static void main(String[] args) {
        UserDao userDao = new UserDaoJdbc();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.print("Введите логин: ");
            String name = scanner.next();
            System.out.print("Введите пароль: ");
            String password = scanner.next();
            if (userDao.auth(name, password) != null) {
                System.out.println("Логин и пароль верные!\n----");
                exit = true;
            }
        }
        while (true) {
            showMenu();
            System.out.println("Введите пункт меню: ");
            String ask = scanner.next();
            if (ADD_USER.equals(ask)) {
                userAdd();
            } else if (ALL.equals(ask)) {
                userDao.getAllClient();
            } else if (ADD_CLIENT.equals(ask)) {
                clientAdd();
            } else if (EXIT.equals(ask)) {
                return;
            }
        }
    }

    static void userAdd() {
        UserDao userDao = new UserDaoJdbc();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Создайте логин: ");
            String log = scanner.next();
            System.out.print("Создайте пароль: ");
            String pass = scanner.next();
            userDao.addUser(new User(log, pass));
            System.out.println("Добавление прошло успешно");
            return;
        }
    }

    static void clientAdd() {
        UserDao userDao = new UserDaoJdbc();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите имя или 0 для выхода");
            String name = scanner.nextLine();
            if (name.equals("0")) {
                return;
            }
            if (validateName(name) >= 2) {
                System.out.println("Введите номер телефона или 0 для выхода");
                String phone = scanner.nextLine();
                if (phone.equals("0")) {
                    return;
                }

                if (validatePhone(phone)) {
                    Client clientFromDb = userDao.getClientByPhoneNumber(phone);
                    if (clientFromDb != null) {
                        userDao.incrementVisitCount(clientFromDb);
                        System.out.println("Клиент с таким номером телефона зарегистрирован, увеличено количество посещений на 1");
                        return;
                    } else {
                        Client client = new Client(name, phone);
                        userDao.addClient(client);
                        System.out.println("Добавление прошло успешно");
                        return;
                    }
                } else {
                    System.out.println("Некорректный номер телефона");
                    break;
                }

            } else {
                System.out.println("Некорректное имя");
            }
        }
    }

    private static Pattern pattern = Pattern.compile("^((\\+7|7|8)+([0-9]){10})$|(9)+([0-9]){9}");

    private static boolean validatePhone(String phone) {

        Matcher matcher = pattern.matcher(phone);
        if (matcher.find()) {
            matcher.group();
            return true;
        } else {
            return false;
        }
    }

    private static int validateName(String name) {
        int a = 0;
        for (int i = 0; i < name.length(); i++) {
            name.charAt(i);
            a++;
        }
        return a;
    }

    private static void showMenu() {
        System.out.println("Меню. ");
        System.out.println("0. Добавить нового сотрудника");
        System.out.println("1. Показать всех клиентов");
        System.out.println("2. Добавить нового клиента");
        System.out.println("3. Выход из программы");
    }
}
