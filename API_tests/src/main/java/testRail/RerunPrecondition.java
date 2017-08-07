package testRail;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

/**
 * Created by wizard on 7/7/16.
 */
public class RerunPrecondition implements IHookable {

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        System.out.println("Starting  " + testResult.getName());

        callBack.runTestMethod(testResult);

        if (testResult.getThrowable()!=null) {

            callBack.runTestMethod(testResult);
        }

    }

}
