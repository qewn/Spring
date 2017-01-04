package screensaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

/**
 * Created by qen on 04.01.2017.
 */
@Configuration
@ComponentScan(basePackages = "screensaver") /*Щоб додатково просканував ще той пакет щоб знайти там той фрейм
що проанотований*/

public class Config {
    @Bean
    @Scope("periodical")
    public Color color(){
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
     }

    @Bean
    public ColorFrame frame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
     }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context =  new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(400);
        }
    }
}
