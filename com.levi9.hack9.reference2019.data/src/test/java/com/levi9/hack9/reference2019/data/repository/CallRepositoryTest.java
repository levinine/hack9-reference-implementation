package com.levi9.hack9.reference2019.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi9.hack9.reference2019.data.model.Call;
import com.levi9.hack9.reference2019.data.JpaConfiguration;;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest(classes = JpaConfiguration.class)
public class CallRepositoryTest {
	private static final Instant MIDNIGHT = Instant.parse("2019-01-01T00:00:00.00Z");
	private static final Instant MIDNIGHT_N = Instant.parse("2019-02-01T00:00:00.00Z");
	private static final Instant NOON = Instant.parse("2019-01-01T12:00:00.00Z");
	private static final Instant NOON_N = Instant.parse("2019-02-01T12:00:00.00Z");
	private static final String CALLER = "+381 64 123456";
	private static final String RECEPIENT = "+381 21 654321";
	private static final String INVOICE_ID = "FACT-381-64-123456";
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CallRepository repository;
	
	private Call midnight = new Call(0, CALLER, RECEPIENT, MIDNIGHT, 200, 20.0F, null);
	private Call midnight_m = new Call(0, CALLER, RECEPIENT, MIDNIGHT_N, 240, 25F, INVOICE_ID);
	
	@Before
	public void setUp() {
		entityManager.persist(midnight);
		entityManager.persist(new Call(0, CALLER, RECEPIENT, NOON, 100, 8F, null));
		entityManager.persist(midnight_m);
		entityManager.persist(new Call(0, CALLER, RECEPIENT, NOON_N, 240, 25F, INVOICE_ID));
	}

	@Test
	public void test_all() {
		List<Call> all = repository.findAll();
		
		assertEquals("Expect 4 elements", 4, all.size());
		assertThat(all).extracting(Call::getInvoiceId).contains(INVOICE_ID);
	}
	
	@Test
	public void test_create() {
		final Instant march = Instant.parse("2019-03-04T00:00:00.00Z");
		final Call newCall = new Call(0, "+381 11 123456", "+381 24 654321", march, 160, 37.2F, null);
		repository.save(newCall);
	}
	
	@Test
	public void test_get_with_invoice() {
		final List<Call> invoiced = repository.getByInvoiceId(INVOICE_ID);
		
		assertEquals("Expect 2 invoices", 2, invoiced.size());
	}
	
	@Test
	public void test_delete() {
		repository.deleteById(midnight.getId());
		
		assertEquals("Expect one less", 3, repository.count());
	}
	
	@Test
	public void test_update() {
		final long id = midnight.getId();
		final Call myMidnight = repository.findById(id).get();
		assertEquals("",  20F, myMidnight.getCost(), 0.01D);
		
		myMidnight.setCost(1000F);
		repository.save(myMidnight);
		
		final Call check = repository.getOne(id);
		assertEquals("Should have updated", 1000F, check.getCost(), 0.01D);
	}
}
