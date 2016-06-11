package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.LoggedUser;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal extends BaseEntity {
    private final Integer userId;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.userId = LoggedUser.id();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", userId=" + userId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
