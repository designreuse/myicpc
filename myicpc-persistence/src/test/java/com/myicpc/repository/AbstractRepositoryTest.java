package com.myicpc.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.myicpc.repository.config.TestPersistenceConfig;
import com.myicpc.repository.contest.ContestRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Roman Smetana
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseTearDown("classpath:dbunit/CleanDatabase.xml")
public abstract class AbstractRepositoryTest extends groovy.util.GroovyTestCase {
    @Autowired
    protected ContestRepository contestRepository;

    protected Date getDate(int year, int month, int day) {
        return new GregorianCalendar(year, month - 1, day).getTime();
    }

    protected Date getDateTime(int year, int month, int day, int hours, int minutes) {
        return new GregorianCalendar(year, month - 1, day, hours, minutes).getTime();
    }
}
