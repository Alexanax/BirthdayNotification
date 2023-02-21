package com.vk.birthdaynotification.dto.members.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vk.birthdaynotification.dto.MultiDateDeserializer;
import java.util.Date;

public class Item {
    private long id;
    private String firstName;
    private String lastName;
    private boolean canAccessClosed;
    private boolean isClosed;
    private Date bdate;

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("first_name")
    public String getFirstName() { return firstName; }
    @JsonProperty("first_name")
    public void setFirstName(String value) { this.firstName = value; }

    @JsonProperty("last_name")
    public String getLastName() { return lastName; }
    @JsonProperty("last_name")
    public void setLastName(String value) { this.lastName = value; }

    @JsonProperty("can_access_closed")
    public boolean getCanAccessClosed() { return canAccessClosed; }
    @JsonProperty("can_access_closed")
    public void setCanAccessClosed(boolean value) { this.canAccessClosed = value; }

    @JsonProperty("is_closed")
    public boolean getIsClosed() { return isClosed; }
    @JsonProperty("is_closed")
    public void setIsClosed(boolean value) { this.isClosed = value; }

    @JsonProperty("bdate")
    @JsonDeserialize(using = MultiDateDeserializer.class)
    public Date getBdate() { return bdate; }
    @JsonProperty("bdate")
    public void setBdate(Date value) { this.bdate = value; }
}
