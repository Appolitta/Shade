package testRail;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;

/**
 * Created by wizard on 7/8/16.
 */
public class InvokedMethodListener implements IInvokedMethodListener {
    private Integer retryCount = 0;
    private Integer retryMaxCount = 5;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("before invocation of " + method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("after invocation " + method.getTestMethod().getMethodName());
        if ((testResult.getThrowable()!=null)&&(testResult.getThrowable().getMessage().contains("java.lang") || testResult.getThrowable().getMessage().contains("java.net") || testResult.getThrowable().getMessage().contains("Broken pipe"))) {
            while (!retry(testResult) && retryCount < retryMaxCount) {
                retryCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Boolean retry(ITestResult tr) {
        try {
            //получаем тип объекта, на кот. вызывается метод
            Object newObject = tr.getInstance();
            //получаем метод класса
            Method method = tr.getMethod().getConstructorOrMethod().getMethod();
            method.invoke(newObject, tr.getParameters());

        } catch (Exception e) {
            if ((e.getMessage().contains("java.lang") || e.getMessage().contains("java.net") || e.getMessage().contains("Broken pipe"))) {
                return false;
            } else {
                return true;
            }
        }
        tr.setStatus(1);
        tr.setThrowable(null);
        return true;
    }
}