package quoters;

/**
 * Created by qen on 02.01.2017.
 */

public class ProfilingController implements ProfilingControllerMBean  {
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

