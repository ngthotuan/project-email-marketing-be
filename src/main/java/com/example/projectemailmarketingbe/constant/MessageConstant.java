package com.example.projectemailmarketingbe.constant;

public class MessageConstant {
    public static String USER_NOT_FOUND = "User has email {} not found";
    public static String USER_EXISTED = "User has email {} existed";
    public static String PROXY_NOT_FOUND = "Proxy not found with Id {}";
    public static String TEMPLATE_NOT_FOUND = "Template not found with Id {}";
    public static String CRON_EXPRESSION_CONFIG_MESSAGE = "Task for notifying users with "
            + "is configured to run with {} cron expression";
    public static String SEND_MAIL_LOG = "Sent mail to email {}";
    public static String RESTART_SCHEDULE = "SCHEDULER IS RESTARTING";
    public static String SCHEDULE_NOT_FOUND = "Schedule with id {} not found";
    public static String SCHEDULE_CRONJOB_RUN_NOT_FOUND = "Schedule cron job run with id {} not found";
}
