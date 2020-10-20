package example.kotlin.teng.githublist.custom.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * A simple callback that can receive from {@link LiveData}.
 *
 * @param <T> The type of the parameter
 * @see LiveData LiveData - for a usage description.
 */
public abstract class LiveDataObserver<T> implements Observer<T> {

    private final LifecycleEvent mLifecycleEvent;

    public LiveDataObserver(@NonNull LifecycleEvent lifecycleEvent) {
        mLifecycleEvent = lifecycleEvent;
    }

    @Override
    public void onChanged(T t) {
        if (Lifecycle.Event.ON_PAUSE.compareTo(mLifecycleEvent.getEvent()) != 0) {
            onContentChanged(t);
        }
    }

    /**
     * Called when the data is changed.
     *
     * @param t The new data
     */
    public abstract void onContentChanged(T t);
}
