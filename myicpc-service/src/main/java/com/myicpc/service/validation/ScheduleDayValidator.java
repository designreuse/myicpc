package com.myicpc.service.validation;

import com.myicpc.model.schedule.ScheduleDay;
import com.myicpc.repository.schedule.ScheduleDayRepository;
import com.myicpc.service.exception.BusinessValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Business validator for {@link ScheduleDay}
 *
 * @author Roman Smetana
 */
@Component
public class ScheduleDayValidator extends BusinessEntityValidator<ScheduleDay> {
    @Autowired
    private ScheduleDayRepository scheduleDayRepository;

    @Override
    public void validate(ScheduleDay scheduleDay) throws BusinessValidationException {
        ScheduleDay duplicated = scheduleDayRepository.findByDayOrderAndContest(scheduleDay.getDayOrder(), scheduleDay.getContest());

        if (duplicated != null && !duplicated.getId().equals(scheduleDay.getId())) {
            throw new BusinessValidationException("scheduleDay.duplicatedDayOrder");
        }
    }
}
