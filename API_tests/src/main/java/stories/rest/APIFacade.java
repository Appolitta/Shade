package stories.rest;

import stories.model.custom.BackendSettings;
import stories.model.user.authenticator.AuthenticationData;
import stories.rest.endpoint.EmployeeEndpoint;
import stories.rest.endpoint.JobEndpoint;
import stories.rest.endpoint.PackageEndpoint;
import stories.rest.endpoint.AccountEndpoint;

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


}

