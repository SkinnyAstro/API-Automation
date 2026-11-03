package core;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;

/** Prints the 4xx/5xx responses captured during a test, but only if that test failed. */
public class FailedResponseLogListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        FailedResponseLog.clear();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        FailedResponseLog.clear();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        FailedResponseLog.clear();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        List<String> entries = FailedResponseLog.drain();
        if (entries.isEmpty()) {
            return;
        }
        System.out.println("---- 4xx/5xx responses during failed test: " + result.getName() + " ----");
        entries.forEach(System.out::println);
        System.out.println("---- end of responses for " + result.getName() + " ----");
    }
}
