package example.kotlin.teng.githublist.custom.livedata;

import androidx.lifecycle.Lifecycle;

public class LifecycleEvent {
    private Lifecycle.Event mEvent = Lifecycle.Event.ON_ANY;

    public void setEvent(Lifecycle.Event event) {
        mEvent = event;
    }

    public Lifecycle.Event getEvent() {
        return mEvent;
    }
}
