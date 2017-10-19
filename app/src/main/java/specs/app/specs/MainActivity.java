package specs.app.specs;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceName();
        versionSO();
        screenSize();
        resolution();
        densityDpi();
    }

    public void deviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String value;
        if (model.startsWith(manufacturer)) {
            value = capitalize(model);
        } else {
            value = capitalize(manufacturer) + " " + model;
        }

        TextView textViewCount = (TextView) findViewById(R.id.phoneNameValue);
        textViewCount.setText(String.valueOf(value));
        Log.d("SPECS","Phone Name: "+ value);
    }

    private void versionSO() {
        String versionNumber = Build.VERSION.RELEASE;
        String versionName = Build.VERSION.RELEASE;
        String versionApi = Build.VERSION.RELEASE;

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                versionName = fieldName;
                versionApi = String.valueOf(fieldValue);
            }
        }

        TextView androidVersionNumber = (TextView) findViewById(R.id.androidVersionNumberValue);
        androidVersionNumber.setText(versionNumber);

        TextView androidVersionName = (TextView) findViewById(R.id.androidVersionNameValue);
        androidVersionName.setText(versionName);

        TextView androidVersionApi = (TextView) findViewById(R.id.androidVersionApiValue);
        androidVersionApi.setText(versionApi);

        Log.d("SPECS","androidVersionNumber: "+ versionNumber);
        Log.d("SPECS","androidVersionName: "+ versionName);
        Log.d("SPECS","androidVersionApi: "+ versionApi);
    }

    private void screenSize() {
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String value;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                value = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                value = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                value = "Small screen";
                break;
            default:
                value = "Screen size is neither large, normal or small";
        }

        TextView textView = (TextView) findViewById(R.id.screenSize);
        textView.setText(value);
        Log.d("SPECS","screenSize: "+value);
    }

    private void resolution() {
        String value;
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        value = "Width: " + String.valueOf(width) + " Height: " + height;

        TextView textView = (TextView) findViewById(R.id.screenResolutionValue);
        textView.setText(value);
        Log.d("SPECS","screenResolution: "+value);

    }

    private void densityDpi() {
        int density = getResources().getDisplayMetrics().densityDpi;
        String value = "Not defined";

        if(density <= DisplayMetrics.DENSITY_LOW){
            value = "DENSITY_LOW - LDPI";
        }else if(density <= DisplayMetrics.DENSITY_MEDIUM){
            value = "DENSITY_MEDIUM - MDPI";
        }else if(density <= DisplayMetrics.DENSITY_HIGH){
            value = "DENSITY_HIGH - HDPI";
        }else if(density <= DisplayMetrics.DENSITY_XHIGH){
            value = "DENSITY_XHIGH - XDPI";
        }else if(density <=DisplayMetrics.DENSITY_XXHIGH){
            value = "DENSITY_XXHIGH - XXDPI";
        }else if(density <= DisplayMetrics.DENSITY_XXXHIGH){
            value = "DENSITY_XXXHIGH - XXXDPI";
        }else if(density > DisplayMetrics.DENSITY_XXXHIGH){
            value = "GREATER THAT!!!  DENSITY_XXXHIGH - XXXDPI";
        }

        TextView textView = (TextView) findViewById(R.id.densityDpiValue);
        textView.setText(value);

        TextView textViewCount = (TextView) findViewById(R.id.densityDpiCountValue);
        textViewCount.setText(String.valueOf(density));

        Log.d("SPECS","densityDpi: "+value);
        Log.d("SPECS","densityDpiCount: "+String.valueOf(density));
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

}
