package mg.arnel.spring_studies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);

		OrderService orderService = context.getBean(OrderService.class);

		orderService.findAll();
		orderService.findById(1);
		orderService.findByStatus("PENDING");
		orderService.findOrderItems(1);
	}
}