package com.volunteerplatform.boundary;

import static com.volunteerplatform.utils.TestUtils.boundaryTestFile;
import static com.volunteerplatform.utils.TestUtils.currentTest;
import static com.volunteerplatform.utils.TestUtils.testReport;
import static com.volunteerplatform.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.volunteerplatform.dto.EventDTO;

public class EventBoundaryTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testNameNotEmpty() throws IOException {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setName("");
		Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDateNotNull() throws IOException {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setDate(null);
		Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testTimeNotNull() throws IOException {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setTime(null);
		Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDescriptionNotEmpty() throws IOException {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setDescription("");
		Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
