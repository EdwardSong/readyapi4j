package com.smartbear.readyapi.client.result;

import com.smartbear.readyapi.client.execution.Execution;
import com.smartbear.readyapi.client.model.HarContent;
import com.smartbear.readyapi.client.model.HarEntry;
import com.smartbear.readyapi.client.model.HarHeader;
import com.smartbear.readyapi.client.model.HarLogRoot;
import com.smartbear.readyapi.client.model.HarResponse;
import com.smartbear.readyapi.client.model.TestStepResultReport;

import java.util.List;

/**
 * Result wrapper for individual TestSteps
 */

public class TestStepResult {
    private final TestStepResultReport testStepResultReport;
    private final Execution execution;
    private boolean hasCheckedForHarEntry;
    private HarEntry harEntry;

    public TestStepResult(TestStepResultReport testStepResultReport, Execution execution){

        this.testStepResultReport = testStepResultReport;
        this.execution = execution;
    }

    public TestStepResultReport assertionStatus(TestStepResultReport.AssertionStatusEnum assertionStatus) {
        return testStepResultReport.assertionStatus(assertionStatus);
    }

    public String getTransactionId() {
        return testStepResultReport.getTransactionId();
    }

    public String getTestStepName() {
        return testStepResultReport.getTestStepName();
    }

    public Long getTimeTaken() {
        return testStepResultReport.getTimeTaken();
    }

    public TestStepResultReport.AssertionStatusEnum getAssertionStatus() {
        return testStepResultReport.getAssertionStatus();
    }

    public List<String> getMessages() {
        return testStepResultReport.getMessages();
    }

    public boolean hasTransactionData(){
        return getHarEntry() != null;
    }

    public HarResponse getHarResponse(){
        HarEntry harEntry = getHarEntry();
        return harEntry == null ? null : harEntry.getResponse();
    }

    public HarEntry getHarEntry() {
        if( harEntry == null && !hasCheckedForHarEntry ) {
            HarLogRoot logRoot = execution.getTestServerApi().getTransactionLog(execution.getId(),
                testStepResultReport.getTransactionId(), execution.getAuth());

            if( logRoot != null && logRoot.getLog() != null && logRoot.getLog().getEntries() != null &&
                logRoot.getLog().getEntries().size() > 0 ) {
                harEntry = logRoot.getLog().getEntries().get(0);
            }

            hasCheckedForHarEntry = true;
        }

        return harEntry;
    }

    public String getResponseContent() {
        HarEntry harEntry = getHarEntry();
        if( harEntry != null ){
            HarContent content = getHarResponse().getContent();
            if( content != null ){
                return content.getText();
            }
        }

        return null;
    }

    public List<HarHeader> getResponseHeaders() {
        HarEntry harEntry = getHarEntry();
        return harEntry == null ? null : harEntry.getResponse().getHeaders();
    }
}
