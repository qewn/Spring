package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by qen on 01.01.2017.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("context.xml"); // Цей клас сканується і аналізується
        // XmlBeanDefinitionReader-ом
        while (true) {
            Thread.sleep(1000);
            context.getBean(Quoter.class).sayQuote(); // Витягую з контексту клас і зразу його метод.
        }
    }
}
                                                  /* Робота Spring */

    /*Є конфігураційний файл Spring який при запуску парситься BeanDefinitionReader-ом, дальше створює такий собі
     * список бінів які там обявлені і складає в BeanDefinitions. Дальше працює ключова фігура Спрінга - Bean Factory
     * який бере Bean Definitions і спочатку з тих BeanDefinitions витягює обявлені BPP, створив їх і відложив
     * в сторону і вже з їх допомогою потім буде настроювати решту бінів. Потім віддає вже настроєні біни
     * BPP де той щось з ними зробить або не зробить і BPP віддає назад і так по черзі всі. Обєкт пройшов першу фазу
     * настройки бінів ДО ІНІТ МЕТОДА(PostContract його опрацював), ПІСЛЯ ІНІТ МЕТОДА ще один прохід і в кінці
     * получаються повністю настроєні обєкти. */
