package com.depuisletemps.mynews.Models;

public class NotificationForm {
    private String queryTerms;
    private boolean arts;
    private boolean books;
    private boolean science;
    private boolean sports;
    private boolean technology;
    private boolean world;
    private boolean notificationSwitch;

    public NotificationForm(String queryTerms, boolean arts, boolean books, boolean science, boolean sports, boolean technology, boolean world, boolean notificationSwitch) {
        this.queryTerms = queryTerms;
        this.arts = arts;
        this.books = books;
        this.science = science;
        this.sports = sports;
        this.technology = technology;
        this.world = world;
        this.notificationSwitch = notificationSwitch;
    }

    public String getQueryTerms() {
        return queryTerms;
    }

    public void setQueryTerms(String queryTerms) {
        this.queryTerms = queryTerms;
    }

    public boolean isArts() {
        return arts;
    }

    public void setArts(boolean arts) {
        this.arts = arts;
    }

    public boolean isBooks() {
        return books;
    }

    public void setBooks(boolean books) {
        this.books = books;
    }

    public boolean isScience() {
        return science;
    }

    public void setScience(boolean science) {
        this.science = science;
    }

    public boolean isSports() {
        return sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
    }

    public boolean isTechnology() {
        return technology;
    }

    public void setTechnology(boolean technology) {
        this.technology = technology;
    }

    public boolean isWorld() {
        return world;
    }

    public void setWorld(boolean world) {
        this.world = world;
    }

    public boolean isNotificationSwitch() {
        return notificationSwitch;
    }

    public void setNotificationSwitch(boolean notificationSwitch) {
        this.notificationSwitch = notificationSwitch;
    }
    
}
