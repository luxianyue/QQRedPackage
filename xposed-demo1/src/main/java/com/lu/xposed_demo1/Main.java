package com.lu.xposed_demo1;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by bulefin on 2017/6/19.
 * 模块的入口，需要在assets文件夹下新建xposed_init文件并写上这个入口类的包名点类名
 */

public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        System.out.println("loadPackageParam.packageName: "+loadPackageParam.packageName);
        //findAndHookMethod()
        //XposedHelpers.findAndHookMethod()
        if(loadPackageParam.packageName.equals("com.tencent.mobileqq")) {
            System.out.println("-------------->: "+loadPackageParam.packageName);
            XposedHelpers.findAndHookMethod("com.tencent.mobileqq.data.MessageForQQWalletMsg", loadPackageParam.classLoader, "doParse",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            System.out.println("beforeHookedMethod-param.size:" + param);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            System.out.println("afterHookedMethod-param.size:" + param);
                        }
                    });

        }

        if(loadPackageParam.packageName.equals("com.bluefin.desinview")) {
            System.out.println("-------------->: "+loadPackageParam.packageName);
           XposedHelpers.findAndHookMethod("com.bluefin.desinview.MainActivity", loadPackageParam.classLoader, "setTextView", String.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    System.out.println("packageClass:com.bluefin.desinview.MainActivity");
                    System.out.println("beforeHookedMethod-param-:" + param);
                    param.args[0] = "被xposed修改的字符";
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    System.out.println("packageClass:com.bluefin.desinview.MainActivity");
                    System.out.println("afterHookedMethod-param------->length:" + param.args.length);
                    for (Object o : param.args) {
                        System.out.println("param-->"+o.toString());
                    }
                }
            });

            XposedHelpers.findAndHookMethod("com.bluefin.desinview.Test", loadPackageParam.classLoader, "test", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    System.out.println("packageClass:com.bluefin.desinview.test------------->");
                    System.out.println("afterHookedMethod-param------->length:" + param.args.length);
                    for (Object o : param.args) {
                        System.out.println("param-->"+o.toString());
                    }
                }
            });
        }

    }
}
