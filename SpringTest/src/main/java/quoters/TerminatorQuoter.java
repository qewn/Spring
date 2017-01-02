package quoters;

import javax.annotation.PostConstruct;

/**
 * Created by qen on 01.01.2017.
 */

@Profiling // Анотація що профілює всі методи даного класу(виводить в лог час роботи його методів)
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 2, max = 7) // Створюю власну анотацію
    private int repeat;
    private String message;

    @PostConstruct // На початку ХМЛ не знає нічого про ніякі анотації, а про них знає BeanPostProcessor тому і
    // працювати не буде доти поки не буде обявлений бін який знає про цю анотацію. -- Двухфазовий конструктор
    public void init() {
        System.out.println("Phase 2");
        System.out.println(repeat);
    }

    public TerminatorQuoter() {
        System.out.println("Phase 1");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sayQuote() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }
}
