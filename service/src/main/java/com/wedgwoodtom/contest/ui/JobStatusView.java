package com.wedgwoodtom.contest.ui;

import com.google.gson.Gson;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Demo view showing how to display JobStatus Info
 */
public class JobStatusView extends VerticalLayout implements View
{
    public static final String NAME = "jobStatus";

    private Grid grid = new Grid();
    private Label label = new Label();

    public JobStatusView()
    {
        VerticalSplitPanel splitPanel = new VerticalSplitPanel();
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(150, Unit.PIXELS);
        splitPanel.setHeight(700, Unit.PIXELS);

        splitPanel.setFirstComponent(grid);
        splitPanel.setSecondComponent(label);


        addComponent(splitPanel);

        label.setContentMode(ContentMode.HTML);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.setSizeFull();
        grid.setColumns("accountTitle", "status", "jobName");

        grid.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                JobStatus bean = (JobStatus) event.getItemId();
//                label.setValue("Job Details:" + bean.getJobDetail().replaceAll("\n", "<br>"));
                // TODO: Set the size small using the theme (not this way)
                label.setValue("Job Details:<span style='font-size:small'>"
                        + bean.getJobDetail().replaceAll("\n", "<br>")
                        + "<span>"
                );
            }
        });

        try
        {
            List<JobStatus> jobStatusList = getJobStatusList();
            if (jobStatusList==null || jobStatusList.isEmpty())
            {
                Notification.show("No JobStatus returned", Notification.Type.ERROR_MESSAGE);
            }

            grid.setContainerDataSource(new BeanItemContainer(JobStatus.class, jobStatusList));
        }
        catch(Exception error)
        {
            Notification.show(error.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
    }

    private List<JobStatus> getJobStatusList() throws IOException
    {
        String token = "9Y-jJKnQctJqX_OIYnoSwSA2oPDeYPDA";

        String response = IOUtils.toString(new URL("http://accountconfiguration.comcastwhls.test.corp.theplatform.com/web/Configure/getJobStatuses?schema=1.0&form=json&token="+token), Charset.forName("UTF-8"));
//        JSONObject json = (JSONObject)new JSONParser().parse(response);

        Gson gson = new Gson();
        GetJobStatusResponse getJobStatusResponse = gson.fromJson(response, GetJobStatusResponse.class);
        return getJobStatusResponse.getJobStatusesResponse;

    }

    public class GetJobStatusResponse
    {
        private List<JobStatus> getJobStatusesResponse = Collections.emptyList();

        public List<JobStatus> getGetJobStatusesResponse()
        {
            return getJobStatusesResponse;
        }

        public void setGetJobStatusesResponse(List<JobStatus> getJobStatusesResponse)
        {
            this.getJobStatusesResponse = getJobStatusesResponse;
        }
    }

    public class JobStatus
    {
        private String accountTitle;
        private String accountId;
        private String jobName;
        private String status;
        private String errorMessage;
        private String correlationId;
        private String jobDetail;

        public String getAccountTitle()
        {
            return accountTitle;
        }

        public void setAccountTitle(String accountTitle)
        {
            this.accountTitle = accountTitle;
        }

        public String getAccountId()
        {
            return accountId;
        }

        public void setAccountId(String accountId)
        {
            this.accountId = accountId;
        }

        public String getJobName()
        {
            return jobName;
        }

        public void setJobName(String jobName)
        {
            this.jobName = jobName;
        }

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public String getErrorMessage()
        {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage)
        {
            this.errorMessage = errorMessage;
        }

        public String getCorrelationId()
        {
            return correlationId;
        }

        public void setCorrelationId(String correlationId)
        {
            this.correlationId = correlationId;
        }

        public String getJobDetail()
        {
            return jobDetail;
        }

        public void setJobDetail(String jobDetail)
        {
            this.jobDetail = jobDetail;
        }
    }
}
