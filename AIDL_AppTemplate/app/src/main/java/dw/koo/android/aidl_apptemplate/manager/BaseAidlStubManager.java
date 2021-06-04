package dw.koo.android.aidl_apptemplate.manager;

import android.content.Context;

import java.util.concurrent.ConcurrentHashMap;

import dw.koo.android.aidl_apptemplate.ApiHandler;
import dw.koo.android.aidl_apptemplate.library.utils.DebugLogger;

public class BaseAidlStubManager {

    private static final String TAG = BaseAidlStubManager.class.getSimpleName();
    protected final ConcurrentHashMap<String, IAidlManagerCallback> mCallbackMap = new ConcurrentHashMap<>();

    protected ApiHandler mApiHandler;
    protected Context mContext;

    public BaseAidlStubManager() {
        DebugLogger.d(TAG, "BaseAidlStubManager is created");
    }

    public void clearCallbackMap() {
        mCallbackMap.clear();
    }
}
