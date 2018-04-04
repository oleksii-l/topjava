package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.datajpa.DatajpaMealServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcMealServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaMealServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JdbcMealServiceTest.class,
        JpaMealServiceTest.class,
        DatajpaMealServiceTest.class
})
public class MealServiceTestSuite {
}
