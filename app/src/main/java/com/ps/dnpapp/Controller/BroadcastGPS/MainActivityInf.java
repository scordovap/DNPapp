package com.ps.dnpapp.Controller.BroadcastGPS;

import java.io.Serializable;

public interface MainActivityInf extends Serializable {
    void DisplayLocationChange(String location);
    void DisplayProviderEnable(boolean isEnabled);
}
