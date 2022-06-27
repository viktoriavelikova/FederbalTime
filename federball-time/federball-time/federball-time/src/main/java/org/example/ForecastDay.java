package org.example;

import com.google.gson.annotations.SerializedName;

public class ForecastDay {
    @SerializedName("date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
