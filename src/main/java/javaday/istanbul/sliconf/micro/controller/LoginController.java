package javaday.istanbul.sliconf.micro.controller;


import javaday.istanbul.sliconf.micro.dao.UserDao;
import javaday.istanbul.sliconf.micro.model.Person;
import javaday.istanbul.sliconf.micro.model.ResponseMessage;
import javaday.istanbul.sliconf.micro.model.User;
import javaday.istanbul.sliconf.micro.provider.LoginControllerMessageProvider;
import javaday.istanbul.sliconf.micro.repository.PersonRepository;
import javaday.istanbul.sliconf.micro.service.PersonRepositoryService;
import javaday.istanbul.sliconf.micro.service.UserService;
import javaday.istanbul.sliconf.micro.util.JsonUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * Created by ttayfur on 7/18/17.
 */
@Component
public class LoginController {

    //private final static Logger logger = LogManager.getLogger(LoginController.class);

    private final static UserDao userDao = new UserDao();


    private LoginControllerMessageProvider loginControllerMessageProvider;

    private PersonRepositoryService personRepositoryService;


    @Autowired
    public LoginController(PersonRepositoryService personRepositoryService,
                           LoginControllerMessageProvider loginControllerMessageProvider) {
        this.loginControllerMessageProvider = loginControllerMessageProvider;
        this.personRepositoryService = personRepositoryService;
    }

    public ResponseMessage test(Request request, Response response) {

        Person person = new Person();
        person.setFirstName("talip");
        person.setLastName("tayfur");

        personRepositoryService.save(person);

        return new ResponseMessage(true, "selam talip", new Object());
    }
    /*

    public ResponseMessage createUser(Request request, Response response) {
        ResponseMessage responseMessage;

        String body = request.body();
        User user = JsonUtil.fromJson(body, User.class);

        User dbUser = userDao.getUser(user);

        // eger user yoksa kayit et
        if (Objects.nonNull(dbUser)) {
            responseMessage = new ResponseMessage(false,
                    loginControllerMessageProvider.getMessage("userNameAlreadyUsed"), new Object());
            return responseMessage;
        }

        user.generateId(); // id bos kalmasin dbye yazarken gerekli

        // Todo make a standalone class for writing to db
        UserService userService = new UserService();
        byte[] salt = userService.getSalt();
        byte[] hashedPassword = userService.getHashedPassword(user.getPassword(), salt);

        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);

        user.setPassword("");

        // todo yazilip yazilmadigini kontrol et
        ResponseMessage dbResponse = userDao.saveUser(user);

        if (!dbResponse.isStatus()) {
            return dbResponse;
        }

        user.setHashedPassword(null);
        user.setSalt(null);

        responseMessage = new ResponseMessage(true,
                loginControllerMessageProvider.getMessage("userSaveSuccessful"), user);

        return responseMessage;
    }

    public ResponseMessage loginUser(Request request, Response response) {
        String body = request.body();
        User requestUser = JsonUtil.fromJson(body, User.class);

        User userFromDB = userDao.getUser(requestUser);

        if (Objects.nonNull(userFromDB) &&
                Objects.nonNull(userFromDB.getHashedPassword()) &&
                Objects.nonNull(userFromDB.getSalt())) {

            UserService userService = new UserService();

            if (userService.checkIfUserAuthenticated(
                    requestUser.getPassword(), userFromDB.getHashedPassword(), userFromDB.getSalt())) {
                return new ResponseMessage(true, "User successfully logged in", userFromDB);
            }

        }

        return new ResponseMessage(false, "Wrong user name or password", new Object());
    }

*/

}
