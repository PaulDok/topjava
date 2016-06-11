package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMealService service;

    public List<UserMeal> getAll() {
        LOG.info("getAll");
        List<UserMeal> meals = service.getAll();
        meals = meals.stream().filter(um -> um.getUserId().equals(LoggedUser.id())).collect(Collectors.toList());
        return meals;
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        UserMeal userMeal = service.get(id);
        if (!userMeal.getUserId().equals(LoggedUser.id())) {
            throw new NotFoundException("Meal with id " + id + " does not belong to user with id " + LoggedUser.id());
        } else {
            return userMeal;
        }
    }

    public UserMeal create(UserMeal userMeal) {
        userMeal.setId(null);
        LOG.info("create " + userMeal);
        return service.save(userMeal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        UserMeal userMeal = service.get(id);
        if (userMeal.getUserId() != LoggedUser.id()) {
            throw new NotFoundException("Meal with id " + id + " does not belong to user with id " + LoggedUser.id());
        } else {
            service.delete(id);
        }
    }

    public void update(UserMeal userMeal, int id) {
        UserMeal oldMeal = service.get(id);
        if (oldMeal.getUserId() != LoggedUser.id()) {
            throw new NotFoundException("Meal with id " + id + " does not belong to user with id " + LoggedUser.id());
        } else {
            userMeal.setId(id);
            LOG.info("update " + userMeal);
            service.update(userMeal);
        }
    }
}