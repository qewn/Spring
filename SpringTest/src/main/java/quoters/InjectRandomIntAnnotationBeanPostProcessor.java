package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by qen on 01.01.2017.
 */
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor { // Інтерефейсл який буде
    // відповідати за обробку всіх бінів класи яких містять цю анотацію хочаб в якомусь полі

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectRandomInt annotation =  field.getAnnotation(InjectRandomInt.class); // Пробую витягнути з поля анотацію
            if (annotation != null) { // Якщо анонотація не рівняється null значить вона там стояла
                int min = annotation.min(); // Витягую тепер два два параметра мін і макс
                int max = annotation.max();
                Random random = new Random();
                int i = min + random.nextInt(max - min); // Генеруєм випадкове число
                field.setAccessible(true); /*Так як поле самособою приватне спочатку отримую до нього доступ
                Дальше можна було просто засетити полю field.set(i) це рандомне число але тоді треба було б його
                try - catch, да і throws кинути не вийде бо імплементується чужий інтерфейс, а якщо він не кидає
                 то і тут не вийде*/
                ReflectionUtils.setField(field, bean, i);//За допомогою даної бібліотеки можна зробити без try -
                // catch(насправді воно просто обгортає і ховає це все діло в RuntimeException).
                // Для якого поля, сам бін(так як кожне поле відповідно в якомусь класі) і власне саме значення
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
