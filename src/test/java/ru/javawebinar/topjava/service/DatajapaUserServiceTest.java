package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {Profiles.DATAJPA, Profiles.ACTIVE_DB})
public class DatajapaUserServiceTest extends UserServiceTest {

    @Test
    public void getUserWithMeal() {
        assertMatchWithMelas(service.get(USER_ID), USER, MEALS);
    }

}
