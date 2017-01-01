package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by qen on 01.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("context.xml"); // Цей клас сканується і аналізується
        // XmlBeanDefinitionReader-ом
        context.getBean(TerminatorQuoter.class).sayQuote(); // Витягую з контексту клас і зразу його метод.
    }
}
