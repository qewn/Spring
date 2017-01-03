package quoters;

import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by qen on 03.01.2017.
 */
public class PropertyFileApplicationContext extends GenericApplicationContext {

    public PropertyFileApplicationContext(String fileName) {
        PropertiesBeanDefinitionReader reader =  new PropertiesBeanDefinitionReader(this); // Приймає той контекст для якого він буде реєструвати його біни коли він їх знайже коли запуститься
        int i = reader.loadBeanDefinitions(fileName); // Загрузити всі біни які він там знайде а ще він вертає кількість знайдених бінів
        System.out.println("Found " + i + " beans");
        refresh();
    }

    public static void main(String[] args) {
        PropertyFileApplicationContext context =  new PropertyFileApplicationContext("context.properties");
        context.getBean(Quoter.class).sayQuote();
    }
}
