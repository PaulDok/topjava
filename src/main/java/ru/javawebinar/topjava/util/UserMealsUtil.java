package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // summarize total calories for each day
        Map<LocalDate, Integer> caloriesByDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            if (caloriesByDay.containsKey(date)) {
                // add to already counted
                caloriesByDay.put(date, caloriesByDay.get(date) + meal.getCalories());
            } else {
                // first entry for a date
                caloriesByDay.put(date, meal.getCalories());
            }
        }

        // filter a set of dates on which calories count was exceeded
        Set<LocalDate> datesExceeded = new HashSet<>();
        for (Map.Entry<LocalDate, Integer> entry : caloriesByDay.entrySet()) {
            if (entry.getValue() > caloriesPerDay)
                datesExceeded.add(entry.getKey());
        }

        // loop through mealList once again - and add the entries to resulting list (with correct exceed flag)
        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalTime time = meal.getDateTime().toLocalTime();
            if (!TimeUtil.isBetween(time, startTime, endTime))
                continue;
            // check if on the day of entry calories count was exceeded
            boolean isExceed = false;
            LocalDate date = meal.getDateTime().toLocalDate();
            if (datesExceeded.contains(date))
                isExceed = true;
            list.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExceed));
        }
        return list;
    }
}
