package org.jhipster.findelec.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.jhipster.findelec.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
