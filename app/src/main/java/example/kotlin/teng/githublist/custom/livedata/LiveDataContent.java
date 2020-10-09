package example.kotlin.teng.githublist.custom.livedata;

public class LiveDataContent<T> {
    private boolean mHasBeenHandled = false;
    private T mContent;

    public void set(T content) {
        mContent = content;
        mHasBeenHandled = false;
    }

    /**
     * Returns the content and prevents its use again.
     */
    public T getContentIfNotHandled() {
        if (mHasBeenHandled) {
            return null;
        } else {
            mHasBeenHandled = true;
            return mContent;
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    public T getContent() {
        mHasBeenHandled = true;
        return mContent;
    }
}
