package demos.okan.nurx.util;

import android.widget.EditText;

public class Utils {

    /**
     * Checks whether the given EditText is Null or Empty.
     *
     * @param editText
     * @return
     */
    public static boolean isNullOrEmpty(EditText editText) {

        if (editText == null) return true;
        if (editText.getText() == null) return true;
        if (editText.getText().toString().equals("")) return true;

        return false;
    }
}
