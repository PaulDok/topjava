package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final UserMeal MEAL1 = new UserMeal(LocalDateTime.parse("2015-05-30 10:00", formatter), "Завтрак", 500);
    public static final int MEAL1ID = MEAL1.getId();
    public static final UserMeal MEAL2 = new UserMeal(LocalDateTime.parse("2015-05-30 13:00", formatter), "Обед", 1000);
    public static final UserMeal MEAL3 = new UserMeal(LocalDateTime.parse("2015-05-30 20:00", formatter), "Ужин", 500);
    public static final UserMeal MEAL4 = new UserMeal(LocalDateTime.parse("2015-05-31 10:00", formatter), "Завтрак", 1000);
    public static final UserMeal MEAL5 = new UserMeal(LocalDateTime.parse("2015-05-31 13:00", formatter), "Обед", 500);
    public static final UserMeal MEAL6 = new UserMeal(LocalDateTime.parse("2015-05-31 20:00", formatter), "Ужин", 510);

}