package org.example;

import com.google.gson.annotations.SerializedName;

public class Hour {

    @SerializedName("time_epoch")
    private long timeEpoch;
    @SerializedName("time")
    private String time;
    @SerializedName("temp_c")
    double tempC;
    @SerializedName("cloud")
    private int cloud;
    @SerializedName("chance_of_rain")
    private int chanceOfRain;
    @SerializedName("gust_kph")
    private double gustKph;

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(int chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public Double getGustKph() {
        return gustKph;
    }

    public void setGustKph(Double gustKph) {
        this.gustKph = gustKph;
    }

    public Long getTimeEpoch() {
        return timeEpoch * 1000;
    }

    public void setTimeEpoch(Long timeEpoch) {
        this.timeEpoch = timeEpoch;
    }

    public Double getTempC() {
        return tempC;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    @Override
    public String toString() {
        return " timeEpoch: " + timeEpoch +  " time: " + time + " tempC: "+ tempC +  " cloud: " + cloud +" chance of rain: " + chanceOfRain + " gustKmh: " + gustKph;
    }
}
