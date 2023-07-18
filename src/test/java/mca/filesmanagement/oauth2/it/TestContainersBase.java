package mca.filesmanagement.oauth2.it;

public abstract class TestContainersBase {

	/*
	@Container
	public static MySQLContainer mySQLContainer = new MySQLContainer(
			"mysql:8.0.30").withDatabaseName("USERS").withUsername("root")
			.withPassword("rootpassword");

	static class Initializer
			implements
				ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(
				ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.url=" + mySQLContainer.getJdbcUrl())
					.applyTo(configurableApplicationContext.getEnvironment());

		}
		
	}
	*/
}
