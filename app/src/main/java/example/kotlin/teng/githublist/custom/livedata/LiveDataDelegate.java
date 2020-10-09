package example.kotlin.teng.githublist.custom.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class LiveDataDelegate<T> {
    private MutableLiveData<LiveDataContent<T>> mLiveData = new MutableLiveData<>();

    public LiveDataDelegate() {
        mLiveData.setValue(new LiveDataContent<>());
    }

    public void setValue(T value) {
        LiveDataContent<T> liveDataContent = mLiveData.getValue();
        if (liveDataContent == null) {
            liveDataContent = new LiveDataContent<>();
        }
        liveDataContent.set(value);
        mLiveData.setValue(liveDataContent);
    }

    public void postValue(T value) {
        LiveDataContent<T> liveDataContent = mLiveData.getValue();
        if (liveDataContent == null) {
            liveDataContent = new LiveDataContent<>();
        }
        liveDataContent.set(value);
        mLiveData.postValue(liveDataContent);
    }

    public T getValue() {
        return mLiveData.getValue() != null ? mLiveData.getValue().getContent() : null;
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super LiveDataContent<T>> observer) {
        mLiveData.observe(owner, observer);
    }

    public void observeForever(@NonNull Observer<? super LiveDataContent<T>> observer) {
        mLiveData.observeForever(observer);
    }

    public void removeObserver(@NonNull final Observer<? super LiveDataContent<T>> observer) {
        mLiveData.removeObserver(observer);
    }

    public void removeObservers(@NonNull final LifecycleOwner owner) {
        mLiveData.removeObservers(owner);
    }

    public boolean hasActiveObservers() {
        return mLiveData.hasActiveObservers();
    }

    public boolean hasObservers() {
        return mLiveData.hasObservers();
    }
}
