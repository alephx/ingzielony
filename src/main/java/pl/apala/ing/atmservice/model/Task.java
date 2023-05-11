package pl.apala.ing.atmservice.model;

import com.jsoniter.annotation.JsonIgnore;

public class Task {

    private int region;

    @JsonIgnore  // w wyniku zwracanym pomijamy to pole
    private int requestType;

    private int atmId;

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    @JsonIgnore // w wyniku zwracanym pomijamy to pole
    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }
}
