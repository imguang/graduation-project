package com.imguang.demo.neo4j;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.imguang.demo.neo4j.dao")
@EnableTransactionManagement
public class MyNeo4jConfiguration extends Neo4jConfiguration {

	@Bean
	public SessionFactory getSessionFactory() {
		return new SessionFactory("com.imguang.demo.neo4j.entity");
	}

	@Bean
	@Qualifier("neo4jTransactionManager")
	public Neo4jTransactionManager neo4jTransactionManager() throws Exception {
		return new Neo4jTransactionManager(getSession());
	}
}
