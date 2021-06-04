// IAidlManagerCallback.aidl
package dw.koo.android.aidl_apptemplate;

// Declare any non-default types here with import statements
import android.os.Bundle;

interface IAidlManagerCallback {
    void onCallbackResult(in String command, in Bundle resultData);
}
