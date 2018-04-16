package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(Profiles.REPOSITORY_IMPLEMENTATION == Profiles.JDBC);
    }
}