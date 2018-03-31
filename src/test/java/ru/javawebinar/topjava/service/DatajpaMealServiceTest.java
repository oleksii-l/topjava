package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.assertMatchWithUser;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(profiles = {Profiles.DATAJPA, Profiles.ACTIVE_DB})
public class DatajpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealsWithUser() {
        assertMatchWithUser(service.getAll(USER_ID), MEALS);
    }
}
