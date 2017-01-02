package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qen on 02.01.2017.
 */

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private ProfilingController controller = new ProfilingController();

    private Map<String, Class> map = new HashMap<String, Class>(); // Мапа яка просто збеігатиме назву обєкта який прийде і його назву

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer(); /*Отримуєм клас через який можна доступитись до можливості
        реєструвати біни*/
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller")); // Метод який власне реєструє бін
    }
    // Можна зайти через Java VVM і знайти той зареєстрований бін і поставити там в полі enabled - true
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) { // Якщо в такому класі наявна така анотація то треба запамятати цей клас
            map.put(beanName, beanClass); // І тепер кладеться в мапу
        }
        return bean; // Тобто на етапі ініціалізації просто запамятовується цей клас з його назвою а вже після виконується реалізовується
    }

    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException { /*Метод
    активується ПІСЛЯ МЕТОДУ ІНІТ. Так як метод до ІНІТУ очікує того що метод вертає саме оригінальний клас який прийшов а тому
    якщо логіку профайлінгу записати в методі ДО ІНІТУ ми отримаєм один обєкт а вернемо інший вже із зміненою бізнес
    логікою. І знаєм що PostContract працює на оригінальний метод */

        Class beanClass = map.get(beanName); // Отримуєм з мапи той клас
        if (beanClass != null) { // Якщо він не налл значить він запамятався а значить над ним стояла анотація
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    new InvocationHandler() {
                        public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
                            if (controller.isEnabled()) { // Якщо відповідно профілювання включене то метод виконується от так:
                                System.out.println("Profiling...");
                                long before =  System.nanoTime();
                                Object retVal = method.invoke(bean, objects);
                                long after = System.nanoTime();
                                System.out.println("Done");
                                System.out.println(after - before);
                                return retVal;
                            } else { // Якщо виключений то відповідно вертається все як і було. Проксі який просто нічого не робить
                                return method.invoke(bean, objects);
                            }
                        }
                    }); // Повертається вже не оригінальний обєкт а новий згенерований за допомогою Dynamic Proxy
        }
        return bean;
    }
}

/* Статичний метод який приймає оригінальний класс і замінює на новий який він же сам згенерую на льоту. Приймає 3 параметра: КлассЛоадер
 * за допомогою якого він клас який прийде загрузиться в систему, клас який приймає інтерефейси які імплементовані в оригінальному і
 * перейдуть в новий та Invocation Handler який інкапсулю в собі логіку яка буде пересена в клас який згенерується на льоту */