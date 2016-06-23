package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    protected DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal meal = service.get(MEAL1ID, LoggedUser.id());
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL1ID, LoggedUser.id());
        List<UserMeal> meals = new ArrayList<>();
        meals.add(MEAL2);
        meals.add(MEAL3);
        meals.add(MEAL4);
        meals.add(MEAL5);
        meals.add(MEAL6);
        MATCHER.assertCollectionEquals(meals, service.getAll(LoggedUser.id()));
    }

    @Test
    public void getBetweenDates() throws Exception {

    }

    @Test
    public void getBetweenDateTimes() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

}