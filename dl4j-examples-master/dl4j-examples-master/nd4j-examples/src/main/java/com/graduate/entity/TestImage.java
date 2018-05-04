package com.graduate.entity;

public class TestImage {
    private Integer id;

    private String location;

    private Integer jpeg;

    private Integer angle;

    private Integer salt;

    private Boolean set;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getJpeg() {
        return jpeg;
    }

    public void setJpeg(Integer jpeg) {
        this.jpeg = jpeg;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public Integer getSalt() {
        return salt;
    }

    public void setSalt(Integer salt) {
        this.salt = salt;
    }

    public Boolean getSet() {
        return set;
    }

    public void setSet(Boolean set) {
        this.set = set;
    }
}