package screensaver;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created by qen on 04.01.2017.
 */
@Component
public class CostumScopeRegistryBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException { // Клас яиий буде реєструвати кастомні скопи
        beanFactory.registerScope("periodical", new PeriodicalScopeConfigurable()); // Приймає назву скопа і конфігураційний клас
    }
}
