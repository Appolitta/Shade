package stories.rest;

import stories.model.custom.BackendSettings;
import stories.model.user.authenticator.AuthenticationData;
import stories.rest.endpoint.*;

import java.io.IOException;

/**
 * Class for access to any backend rest endpoints.
 * You can create different instances of that class to work with different instances of the backend.
 */
public class APIFacade {
    private final Rest rest;
    private BackendSettings backendSettings;


    private AccountEndpoint accountEndpoint;
    private PackageEndpoint packageEndpoint;
    private JobEndpoint jobEndpoint;
    private EmployeeEndpoint employeeEndpoint;
    private EmployerEndpoint employerEndpoint;
    private NotificationEndpoint notificationEndpoint;

    public APIFacade(AuthenticationData authData, BackendSettings backendSettings)
            throws IOException {
        if (null == backendSettings) {
            throw new IllegalArgumentException("You should provide backend settings.");
        }
        this.backendSettings = backendSettings;
        this.rest = new RestDefaultImpl(backendSettings, authData);
    }

    public BackendSettings getBackendSettings() {
        return backendSettings;
    }



    public PackageEndpoint getPackageEndpoint() {
        if (null == packageEndpoint) {
            packageEndpoint = new PackageEndpoint(rest);
        }
        return packageEndpoint;
    }

    /*-------Account------------*/
    public AccountEndpoint getAccountEndpoint() {
        if (null == accountEndpoint) {
            accountEndpoint = new AccountEndpoint(rest);
        }
        return accountEndpoint;
    }

    public AccountEndpoint getAccount(){
        if (null == accountEndpoint) {
            accountEndpoint = new AccountEndpoint(rest);
        }
        return accountEndpoint;
    }
    /*--------Job-------------*/
    public JobEndpoint getJobEndpoint() {
        if (null == accountEndpoint) {
            jobEndpoint = new JobEndpoint(rest);
        }
        return jobEndpoint;
    }

    public JobEndpoint getJob(){
        if (null == accountEndpoint) {
            jobEndpoint = new JobEndpoint(rest);
        }
        return jobEndpoint;
    }

    /*------------Employee---------*/
    public EmployeeEndpoint getEmployeeEndpoint(){
        if (null == employeeEndpoint) {
            employeeEndpoint = new EmployeeEndpoint(rest);
        }
        return employeeEndpoint;
    }

    public EmployeeEndpoint getEmployee(){
        if (null == accountEndpoint) {
            employeeEndpoint = new EmployeeEndpoint(rest);
        }
        return employeeEndpoint;
    }

    /*------------Employer---------*/
    public EmployerEndpoint getEmployerEndpoint(){
        if (null == employerEndpoint) {
            employerEndpoint = new EmployerEndpoint(rest);
        }
        return employerEndpoint;
    }

    public EmployerEndpoint getEmployer(){
        if (null == accountEndpoint) {
            employerEndpoint = new EmployerEndpoint(rest);
        }
        return employerEndpoint;
    }

    /*---------Notification----------------*/

    public NotificationEndpoint getNofication(){
        if (null == notificationEndpoint) {
            notificationEndpoint = new NotificationEndpoint(rest);
        }
        return notificationEndpoint;
    }

    public NotificationEndpoint getNotification() {
        if (null == notificationEndpoint) {
            notificationEndpoint = new NotificationEndpoint(rest);
        }
        return notificationEndpoint;
    }
}

