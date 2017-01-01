package quoters;

/**
 * Created by qen on 01.01.2017.
 */
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 2, max = 7) // Створюю власну анотацію
    private int repeat;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void sayQuote() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }
}
