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

    public JobStatusView()
    {
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        // Add columns to the grid
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(700, Unit.PIXELS);
        grid.setColumns("accountTitle", "status", "jobName");
        addComponent(grid);


        grid.setDetailsGenerator(new Grid.DetailsGenerator() {
            @Override
            public Component getDetails(Grid.RowReference rowReference) {
                // Find the bean to generate details for

                final JobStatus bean = (JobStatus) rowReference.getItemId();

                // A basic label with bean data
                Label label = new Label("Job Details:" + bean.getJobDetail().replaceAll("\n", "<br>"));
                label.setContentMode(ContentMode.HTML);

                // A button just for the sake of the example
                Button button = new Button("Click me", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Notification.show("Some button");
                    }
                });

                // Wrap up all the parts into a vertical layout
                VerticalLayout layout = new VerticalLayout(label,  button);
                layout.setSpacing(true);
                layout.setMargin(true);
                return layout;
            }
        });

        grid.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Object itemId = event.getItemId();
                    grid.setDetailsVisible(itemId, !grid.isDetailsVisible(itemId));
                }
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
        String token = "KyXtg4N87xf2KaPOovom4aAo4BCqYLBO";

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
