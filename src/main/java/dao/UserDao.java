package dao;

import model.Client;
import model.User;

public interface UserDao {

    Client getClientById(long id);

    Client getClientByPhoneNumber(String phone);

    void getAllClient();

    void addClient(Client client);

    void addUser(User user);

    User auth(String login, String pass);

    void incrementVisitCount(Client client);
}
