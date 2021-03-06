package com.letv.plugin.pluginloader.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.letv.plugin.pluginloader.application.JarApplication;
import com.letv.plugin.pluginloader.loader.JarContext;
import com.letv.plugin.pluginloader.loader.JarLayoutInflater;
import com.letv.plugin.pluginloader.loader.JarResOverrideInterface;

public abstract class JarBaseActivity {
    public static final String EXTRA_CLASS = "extra.class";
    public static final String EXTRA_JARNAME = "extra.jarname";
    public static final String EXTRA_PACKAGENAME = "extra.packagename";
    public static final String PROXY_VIEW_ACTION = "com.letv.plugin.pluginloader.proxyactivity.VIEW";
    private static final String TAG = "JarBaseActivity";
    protected JarContext context;
    protected String jar_packagename;
    protected String jarname;
    protected Activity proxyActivity;
    protected ViewGroup root;

    protected abstract ClassLoader getClassLoader();

    protected void onCreate(Bundle savedInstanceState) {
    }

    public void setProxy(Activity proxyActivity, String jarname, String jar_packagename) {
        this.proxyActivity = proxyActivity;
        this.jarname = jarname;
        this.jar_packagename = jar_packagename;
        this.root = (ViewGroup) proxyActivity.findViewById(16908290);
        setJarResource(true);
        this.context = new JarContext(proxyActivity, getClassLoader());
    }

    protected void startActivityByProxy(String className) {
        Intent intent = new Intent("com.letv.plugin.pluginloader.proxyactivity.VIEW");
        intent.putExtra("extra.jarname", this.jarname);
        intent.putExtra("extra.packagename", this.jar_packagename);
        intent.putExtra("extra.class", className);
        this.proxyActivity.startActivity(intent);
    }

    public void startActivityForResultByProxy(String className, int requestCode) {
        Intent intent = new Intent("com.letv.plugin.pluginloader.proxyactivity.VIEW");
        intent.putExtra("extra.jarname", this.jarname);
        intent.putExtra("extra.packagename", this.jar_packagename);
        intent.putExtra("extra.class", className);
        this.proxyActivity.startActivityForResult(intent, requestCode);
    }

    public void setContentView(View view) {
        this.root.addView(view);
    }

    public void setContentView(View view, LayoutParams params) {
        this.root.addView(view, params);
    }

    public void setContentView(int layoutResID) {
        JarLayoutInflater.from(this.context).inflate(layoutResID, this.root);
    }

    public void addContentView(View view, LayoutParams params) {
        this.root.addView(view, params);
    }

    public View findViewById(int id) {
        return this.proxyActivity.findViewById(id);
    }

    public Resources getResources() {
        return this.proxyActivity.getResources();
    }

    protected void onRestart() {
    }

    protected void onStart() {
    }

    protected void onResume() {
    }

    protected void onPause() {
    }

    protected void onStop() {
    }

    protected void onDestroy() {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
    }

    protected void onNewIntent(Intent intent) {
    }

    protected void onSaveInstanceState(Bundle outState) {
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onBackPressed() {
    }

    public Activity getActivity() {
        return this.proxyActivity;
    }

    public Context getContext() {
        return this.context;
    }

    public void setJarResource(boolean isJar) {
        if (isJar) {
            ((JarResOverrideInterface) this.proxyActivity).setResourcePath(true, this.jarname, this.jar_packagename);
        } else {
            ((JarResOverrideInterface) this.proxyActivity).setResourcePath(false, null, null);
        }
    }

    public String getJarName() {
        return this.jarname;
    }

    public String getJarPackageName() {
        return this.jar_packagename;
    }

    public Context getMainApplication() {
        return JarApplication.getInstance();
    }
}
