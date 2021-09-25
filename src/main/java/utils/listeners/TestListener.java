package utils.listeners;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.*;

import static datastore.TreadLocalStore.dataStore;

public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
    }

    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
    }

    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);

//        // List of test results which we will delete later
//        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
//        // collect all id's from passed test
//        Set<Integer> passedTestIds = new HashSet<Integer>();
//        for (ITestResult passedTest : testContext.getPassedTests()
//                .getAllResults()) {
//            // logger.info("PassedTests = " + passedTest.getName());
//            passedTestIds.add(getId(passedTest));
//        }
//
//        Set<Integer> failedTestIds = new HashSet<Integer>();
//        for (ITestResult failedTest : testContext.getFailedTests()
//                .getAllResults()) {
//            // logger.info("failedTest = " + failedTest.getName());
//            // id = class + method + dataprovider
//            int failedTestId = getId(failedTest);
//
//            // if we saw this test as a failed test before we mark as to be
//            // deleted
//            // or delete this failed test if there is at least one passed
//            // version
//            if (failedTestIds.contains(failedTestId)
//                    || passedTestIds.contains(failedTestId)) {
//                testsToBeRemoved.add(failedTest);
//            } else {
//                failedTestIds.add(failedTestId);
//            }
//        }
//
//        // finally delete all tests that are marked
//        for (Iterator<ITestResult> iterator =
//
//             testContext.getFailedTests().getAllResults().iterator(); iterator
//                     .hasNext(); ) {
//            ITestResult testResult = iterator.next();
//
//            if (testsToBeRemoved.contains(testResult)) {
//                // logger.info("Remove repeat Fail Test: " +
//                // testResult.getName());
//                iterator.remove();
//            }
//        }
        //结束时清理本地线程缓存
        dataStore.remove();
    }

    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id
                + (result.getParameters() != null ? Arrays.hashCode(result
                .getParameters()) : 0);
        return id;
    }
}
