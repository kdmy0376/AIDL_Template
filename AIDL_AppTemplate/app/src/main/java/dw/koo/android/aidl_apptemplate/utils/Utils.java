package dw.koo.android.aidl_apptemplate.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Message;
import android.os.RemoteException;

import java.util.Iterator;

import dw.koo.android.aidl_apptemplate.data.CallbackData;
import static dw.koo.android.aidl_apptemplate.data.Constants.*;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static String getPid(String packageName,
                                ConcurrentHashMap<String, IAidlManagerCallback> callbackMap) {

        Iterator<String> iterator = callbackMap.keySet().iterator();
        while (iterator.hasNext()) {

            String key = iterator.next();
            String[] splitedCallbackId = key.split(",");
            if (packageName.equals(splitedCallbackId[1])) {
                return splitedCallbackId[0];
            }
        }
        return null;
    }

    public static void sendCallbackResult(boolean forNotify, Context context, IAidlManagerCallback callback,
                                          ConcurrentHashMap<String, IAidlManagerCallback> callbackMap,
                                          Bundle result, String command) {
        try {
            callback.onCallbackResult(command, result);
        } catch (DeadObjectException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void recoverClient(Context context, String action, String packageName) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setPackage(packageName);
        context.sendBroadcast(intent);
    }

    public static CallbackData getExtensionCallbackData(
            String packageName, ConcurrentHashMap<String, IAidlManagerCallback> callbackMap) {

        Iterator<String> iterator = callbackMap.keySet().iterator();
        while (iterator.hasNext()) {

            String key = iterator.next();
            String[] splitedCallbackId = key.split(",");
            if (packageName.equals(splitedCallbackId[1])) {
                IAidlManagerCallback callback = callbackMap.get(key);
                CallbackData callbackData = new CallbackData();
                callbackData.setManagerCallback(callback).
                        setCallbackPid(Integer.parseInt(splitedCallbackId[0])).
                        setCallbackPackageName(splitedCallbackId[1]);
                return callbackData;
            }
        }
        return null;
    }

    public static Bundle inspectMessage(
            Message message, int apiType,
            ConcurrentHashMap<String, IAidlManagerCallback> callbackMap) {
        Bundle result;
        if (isCorrectMessageWhat(message, apiType)) {
            result = getResultBundle(message);
            if (result == null) {
                return null;
            } else {
                if (!isAvailableCallbackMap(callbackMap)) {
                    return null;
                }
                return result;
            }
        } else {
            return null;
        }
    }

    public static Bundle getExaminedParams(Bundle params) {
        if (params == null) {
            return null;
        }
        return params;
    }

    public static Bundle getInitializedBundle(Bundle params) {
        if (params == null) {
            return null;
        }

        String commandTag = params.getString(KEY_COMMAND);
        String extensionName = params.getString(KEY_PACKAGE_NAME);
        int extensionPid = params.getInt(KEY_PID);

        params.putString(KEY_COMMAND, commandTag);
        params.putString(KEY_PACKAGE_NAME, extensionName);
        params.putInt(KEY_PID, extensionPid);
        params.putString(KEY_CALLBACK_NAME, commandTag);
        return params;
    }

    private static Bundle getResultBundle(Message message) {
        if (message.obj == null) {
            return null;
        }
        if (message.obj instanceof Bundle) {
            return (Bundle) message.obj;
        }
        return null;
    }

    private static boolean isCorrectMessageWhat(Message message, int apiType) {
        if (message.what == apiType) {
            return true;
        }
        return false;
    }

    private static boolean isAvailableCallbackMap(ConcurrentHashMap<String, IAidlManagerCallback> callbackMap) {
        if (callbackMap == null) {
            return false;
        }
        return true;
    }
}