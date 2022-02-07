package group.brewdaytwo.config;

import javax.sql.DataSource;

import group.brewdaytwo.attrezzo.dao.AttrezzoDAO;
import group.brewdaytwo.attrezzo.dao.AttrezzoDAOImpl;
import group.brewdaytwo.ingrediente.dao.IngredienteDAO;
import group.brewdaytwo.ingrediente.dao.IngredienteDAOImpl;
import group.brewdaytwo.ricetta.dao.RicettaDAO;
import group.brewdaytwo.ricetta.dao.RicettaDAOImpl;
import group.brewdaytwo.utente.dao.UtenteDAO;
import group.brewdaytwo.utente.dao.UtenteDAOImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages="group.brewdaytwo")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/progetto_brewday?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("admin");

		return dataSource;
	}

	@Bean
	public UtenteDAO getUtenteDAO() {
		return new UtenteDAOImpl(getDataSource());
	}

	@Bean
	public RicettaDAO getRicettaDAO() {
		return new RicettaDAOImpl(getDataSource());
	}

	@Bean
	public IngredienteDAO getIngredienteDAO() {
		return new IngredienteDAOImpl(getDataSource());
	}

	@Bean
	public AttrezzoDAO getAttrezzoDAO() {
		return new AttrezzoDAOImpl(getDataSource());
	}

}
