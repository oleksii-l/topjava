package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DatajapaUserServiceTest extends UserServiceTest {

    @Test
    public void getUserWithMealFetch() {
        assertMatchWithMelas(service.getWithMeals(USER_ID), USER, MEALS);
    }

}
