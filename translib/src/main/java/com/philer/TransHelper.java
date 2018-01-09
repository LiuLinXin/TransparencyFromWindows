package com.philer;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

/**
 * Created by sober_philer on 2018/1/9.
 * description:透明状态栏和虚拟键盘帮助类
 * new TransHelper(Activity).QtransContent
 */

public class TransHelper {

    private Activity activity;

    public TransHelper(Activity activity) {
        this.activity = activity;
    }

    /**
     *
     * @param color 需要设置的颜色
     */
    public void transContent(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int navigationBarHeight = getBarHeight(activity);
            ViewGroup container = (ViewGroup) activity.getWindow().getDecorView();
            ViewGroup contanerView = null;
            LinearLayout linearLayout = null;
            while (linearLayout == null) {
                container = (ViewGroup) container.getChildAt(0);
                if (container instanceof LinearLayout) {
                    linearLayout = (LinearLayout) container;
                }
            }
            if (navigationBarHeight > 0) {
                while (contanerView == null) {
                    View childAt = linearLayout.getChildAt(1);
                    if (childAt instanceof ViewGroup) {
                        contanerView = (ViewGroup) childAt;
                    }
                }
            }

            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            View view = new View(activity);
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight(activity)));
            view.setBackgroundColor(color);
            linearLayout.addView(view, 0);

            if (navigationBarHeight > 0) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contanerView.getLayoutParams();
                layoutParams.height = 0;
                layoutParams.weight = 1;
                contanerView.setLayoutParams(layoutParams);

                if (navigationBarHeight > 0) {
                    View viewBottom = new View(activity);
                    viewBottom.setLayoutParams(new LinearLayout.LayoutParams(-1, navigationBarHeight));
                    viewBottom.setBackgroundColor(color);
                    linearLayout.addView(viewBottom);
                }
            }
        }
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("status_bar_height").get(object).toString();
            int height = Integer.parseInt(heightStr);
            //dp--->px
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取虚拟键盘的高度
     * @param context
     * @return
     */
    private int getNavigationBarHeight(Context context) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("navigation_bar_height").get(object).toString();
            int height = Integer.parseInt(heightStr);
            //dp--->px
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    private int getBarHeight(Context context){
        return getHasVirtualKey() - getNoHasVirtualKey();
    }

    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    private int getNoHasVirtualKey() {
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return
     */
    private int getHasVirtualKey() {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }
}
