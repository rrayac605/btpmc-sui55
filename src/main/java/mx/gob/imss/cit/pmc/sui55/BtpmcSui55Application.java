package mx.gob.imss.cit.pmc.sui55;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BtpmcSui55Application {

	public static void main(String[] args) {
		SpringApplication.run(BtpmcSui55Application.class, args);
	}

}
