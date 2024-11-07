package group8.spartan_games_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpartanGamesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpartanGamesAppApplication.class, args);
	}

}
