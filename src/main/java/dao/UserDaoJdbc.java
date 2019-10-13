package dao;

import model.Client;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    @Override
    public Client getClientById(long id) {
        String getAllClientSql = "SELECT * FROM client WHERE id = " + id;
        return clientDB(getAllClientSql);
    }

    @Override
    public Client getClientByPhoneNumber(String phone) {
        String getAllClientSql = "SELECT * FROM client WHERE number = " + phone;
        return clientDB(getAllClientSql);
    }

    public Client clientDB(String c) {
        try (Connection connection = DbTestConnector.connect();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(c);
            while (resultSet.next()) {
                Long clientId = resultSet.getLong(1);
                String clientName = resultSet.getString(2);
                String clientNumber = resultSet.getString(3);
                int visitCount = resultSet.getInt(4);
                Client client = new Client(clientId, clientName, clientNumber, visitCount);
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void getAllClient() {
        String getAllClientSql = "SELECT * FROM client";
        List<Client> clients = new ArrayList<>();

        clients.add(clientDB(getAllClientSql));

        System.out.println(String.format("%3s %1s %8s %5s %8s %5s %11s %1s", "id", "|", "name", "|", "number", "|", "visit count", "|"));
        System.out.println(String.format("%s", "-------------------------------------------------"));
        for (Client client : clients) {
            System.out.println(String.format("%3s %1s %12s %1s %12s %1s %6s %6s", client.getId(), "|", client.getName(), "|", client.getNumber(), "|", client.getVisitCount(), "|"));
        }

    }

    @Override
    public void addClient(Client client) {
        String insertClientSql = "INSERT INTO client (name, number) VALUES ('"
                + client.getName() + "', '" + client.getNumber() + "')";
        try (Connection connection = DbTestConnector.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertClientSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addUser(User user) {
        String insertClientSql = "INSERT INTO user (login, password) VALUES ('"
                + user.getLogin() + "', '" + user.getPassword() + "')";
        try (Connection connection = DbTestConnector.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertClientSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User auth(String login, String pass) {
        String userLogin = "";
        String userPass = "";

        String authUser = "SELECT * FROM user WHERE login = '" + login + "' AND password = '" + pass + "'";
        try (Connection connection = DbTestConnector.connect();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(authUser);
            while (resultSet.next()) {
                Long userId = resultSet.getLong(1);
                userLogin = resultSet.getString(2);
                userPass = resultSet.getString(3);
                User user = new User(userId, userLogin, userPass);
                return user;
            }

            if (!login.equals(userLogin) || !pass.equals(userPass)) {
                System.out.println("Некорректный логин или пароль\n----");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * +1 к посещению, если номер телефона совпадает
     * @param client
     */
    @Override
    public void incrementVisitCount(Client client) {
        Client clientFromDb = getClientByPhoneNumber(client.getNumber());
        int newVisitCount = clientFromDb.getVisitCount() + 1;
        String insertClientSql = "UPDATE client SET visit_count = " + newVisitCount + " WHERE id = " + clientFromDb.getId();
        try (Connection connection = DbTestConnector.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertClientSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
