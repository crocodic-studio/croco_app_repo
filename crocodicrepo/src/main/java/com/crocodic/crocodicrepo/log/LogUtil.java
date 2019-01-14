package com.crocodic.crocodicrepo.log;

import java.util.Arrays;
import java.util.List;

public class LogUtil {

    public interface ILog {
        void d(String msg);

        void w(Throwable e);

        void e(String msg);

        void e(String msg, Throwable e);

        String getTag();

        boolean isDebug();

        String preProcessMessage(String msg);
    }

    public static void setLogger(ILog logger) {
        LogUtil.logger = logger;
    }

    static ILog logger = new ConCreateLog() {
        @Override
        public String getTag() {
            return "vv";
        }

        @Override
        public boolean isDebug() {
            return false;
        }

    };

    public static void d(Object msg) {
        logger.d(String.valueOf(msg));
    }

    public static void w(Throwable e) {
        logger.w(e);
    }

    public static void e(Object msg) {
        logger.e(String.valueOf(msg));
    }

    public static void e(Object msg, Throwable e) {
        logger.e(String.valueOf(msg), e);
    }

    public static abstract class ConCreateLog implements ILog {

        @Override
        public void d(String msg) {
            if (isDebug()) android.util.Log.d(getTag(), preProcessMessage(msg));
        }

        @Override
        public void w(Throwable e) {
            if (isDebug()) android.util.Log.d(getTag(), preProcessMessage(""), e);
        }

        @Override
        public void e(String msg) {
            if (isDebug()) android.util.Log.e(getTag(), preProcessMessage(msg));
        }

        @Override
        public void e(String msg, Throwable e) {
            if (isDebug()) android.util.Log.e(getTag(), preProcessMessage(msg), e);
        }


        @Override
        public String preProcessMessage(String msg) {
            return getMethodName(3) + " " + msg;
        }

    }


    private static final List<String> EXCLUDE_CLASSES = Arrays.asList(
            "dalvik.system.VMStack", "java.lang.Thread",
            LogUtil.class.getCanonicalName());


    private static String getMethodName(int ignoreDepth) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();

        boolean find = false;
        for (int i = 0; i < stacks.length; i++) {
            StackTraceElement stack = stacks[i];
            String stackClass = stack.getClassName();
            if (!find && !EXCLUDE_CLASSES.contains(stackClass)) {
                find = true;
            }
            if (find && ignoreDepth == 0) {
                return stackToString(stack);
            } else if (find) {
                ignoreDepth--;
            }
        }

        return null;
    }

    private static String stackToString(StackTraceElement stack) {
        return stack.getFileName()
                .replace(".java", "") +
                "#" +
                stack.getMethodName() +
                "/L" +
                stack.getLineNumber();
    }


}