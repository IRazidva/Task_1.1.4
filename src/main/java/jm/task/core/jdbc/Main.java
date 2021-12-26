package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        User user = new User("Irina","Razumova", (byte) 43);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");

        user.setName("Alexandr");
        user.setLastName("Martinets");
        user.setAge((byte) 43);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");

        user.setName("Rita");
        user.setLastName("Sergeeva");
        user.setAge((byte) 36);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");

        user.setName("German");
        user.setLastName("Sevastianov");
        user.setAge((byte) 26);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");

        for(User person : userService.getAllUsers()) {
            System.out.println(person.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
