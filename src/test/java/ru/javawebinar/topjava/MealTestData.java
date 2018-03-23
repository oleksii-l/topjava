package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static int MEAL_SEQ = START_SEQ + 2;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(MEAL_SEQ+5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(MEAL_SEQ+4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(MEAL_SEQ+3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(MEAL_SEQ+2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(MEAL_SEQ+1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_SEQ, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)
    );

    public static int ADMIN_MEAL_SEQ = START_SEQ + 8;
    public static final Meal ADMIN_MEAL = new Meal(ADMIN_MEAL_SEQ, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак_Admin", 500);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }


}
