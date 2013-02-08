package com.keybox.manage.action;


import com.keybox.manage.db.SystemDB;
import com.keybox.manage.model.HostSystem;
import com.keybox.manage.model.SortedSet;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * Action to manage systems
 */
public class SystemAction extends ActionSupport {

    SortedSet sortedSet = new SortedSet();
    HostSystem hostSystem = new HostSystem();

    @Action(value = "/manage/viewSystems",
            results = {
                    @Result(name = "success", location = "/manage/view_systems.jsp")
            }
    )
    public String viewSystems() {
        sortedSet = SystemDB.getSystemSet(sortedSet);
        return SUCCESS;
    }

    @Action(value = "/manage/saveSystem",
            results = {
                    @Result(name = "input", location = "/manage/view_systems.jsp"),
                    @Result(name = "success", location = "/manage/viewSystems.action?sortedSet.orderByDirection=${sortedSet.orderByDirection}&sortedSet.orderByField=${sortedSet.orderByField}", type = "redirect")
            }
    )
    public String saveSystem() {

        if (hostSystem.getId() != null) {
            SystemDB.updateSystem(hostSystem);
        } else {
            SystemDB.insertSystem(hostSystem);

        }
        return SUCCESS;
    }

    @Action(value = "/manage/deleteSystem",
            results = {
                    @Result(name = "success", location = "/manage/viewSystems.action?sortedSet.orderByDirection=${sortedSet.orderByDirection}&sortedSet.orderByField=${sortedSet.orderByField}", type = "redirect")
            }
    )
    public String deleteSystem() {

        if (hostSystem.getId() != null) {
            SystemDB.deleteSystem(hostSystem.getId());
        }
        return SUCCESS;
    }

    /**
     * Validates all fields for adding a host system
     */
    public void validateSaveSystem() {
        if (hostSystem == null
                || hostSystem.getDisplayNm() == null
                || hostSystem.getDisplayNm().trim().equals("")) {
            addFieldError("hostSystem.displayNm", "Display Name is required");
        }
        if (hostSystem == null
                || hostSystem.getUser() == null
                || hostSystem.getUser().trim().equals("")) {
            addFieldError("hostSystem.user", "User is required");
        }
        if (hostSystem == null
                || hostSystem.getHost() == null
                || hostSystem.getHost().trim().equals("")) {
            addFieldError("hostSystem.host", "Host is required");
        }
        if (hostSystem == null
                || hostSystem.getPort() == null) {
            addFieldError("hostSystem.port", "Port is required");
        } else if (!(hostSystem.getPort() > 0)) {
            addFieldError("hostSystem.port", "Port is invalid");
        }

        if (hostSystem == null
                || hostSystem.getAuthorizedKeys() == null
                || hostSystem.getAuthorizedKeys().trim().equals("")) {
            addFieldError("hostSystem.authorizedKeys", "Authorized Keys is required");
        }

        if (!this.getFieldErrors().isEmpty()) {

            sortedSet = SystemDB.getSystemSet(sortedSet);
        }

    }


    public HostSystem getHostSystem() {
        return hostSystem;
    }

    public void setHostSystem(HostSystem hostSystem) {
        this.hostSystem = hostSystem;
    }

    public SortedSet getSortedSet() {
        return sortedSet;
    }

    public void setSortedSet(SortedSet sortedSet) {
        this.sortedSet = sortedSet;
    }
}
