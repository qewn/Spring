package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import quoters.Anotations.DeprecatedClass;

/**
 * Created by qen on 03.01.2017.
 */
public class DeprecationHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor { /*Щоб замінити біни на нові ще до
    їх створення*/

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
       String[] names = beanFactory.getBeanDefinitionNames(); // Назви всіх бінів
        for (String name : names) {
            BeanDefinition beanDefinition =  beanFactory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> beanClass = Class.forName(beanClassName);
                DeprecatedClass anotation = beanClass.getAnnotation(DeprecatedClass.class);
                if (anotation != null) {
                    beanDefinition.setBeanClassName(anotation.newImpl().getName()); // Витягнув назву нового класу
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
