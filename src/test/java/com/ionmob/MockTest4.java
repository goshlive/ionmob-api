package com.ionmob;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.ionmob.model.ReminderDetail;
import com.ionmob.repo.ReminderRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MockTest4 {

	@Autowired
	private ReminderRepository reminderRepository;
	
	@Test
	@Rollback(false)
	void test() {
		List<ReminderDetail> reminders = reminderRepository.findDetail(121447);
		Iterator<ReminderDetail> it = reminders.iterator();
		while (it.hasNext()) {
			ReminderDetail reminder = it.next();
		}
	}
}