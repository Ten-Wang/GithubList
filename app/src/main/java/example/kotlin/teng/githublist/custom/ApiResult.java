package example.kotlin.teng.githublist.custom;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ApiResult {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            ERROR, SUCCESS, ERROR_AUTH
    })
    public @interface Result {

    }

    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR_AUTH = 2;

    private int mResult;
    private String mMessage;
    private String mErrCode;

    public ApiResult(@Result int result, String message, String errCode) {
        mResult = result;
        mMessage = message;
        if (errCode == null) {
            mErrCode = "";
        } else {
            mErrCode = errCode;
        }
    }

    public @Result
    int getResult() {
        return mResult;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getErrCode() {
        return mErrCode;
    }
}
