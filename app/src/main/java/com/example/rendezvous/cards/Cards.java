package com.example.rendezvous.cards;

public class Cards {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String kitchenCountryNeed;
    private String favDishGive;
    private String budget;

    public Cards() {
    }

    public Cards(String userId, String name, String profileImageUrl, String kitchenCountryNeed, String favDishGive, String budget) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.kitchenCountryNeed = kitchenCountryNeed;
        this.favDishGive = favDishGive;
        this.budget = budget;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getKitchenCountryNeed() {
        return kitchenCountryNeed;
    }

    public void setKitchenCountryNeed(String kitchenCountryNeed) {
        this.kitchenCountryNeed = kitchenCountryNeed;
    }

    public String getFavDishGive() {
        return favDishGive;
    }

    public void setFavDishGive(String favDishGive) {
        this.favDishGive = favDishGive;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", kitchenCountryNeed='" + kitchenCountryNeed + '\'' +
                ", favDishGive='" + favDishGive + '\'' +
                ", budget='" + budget + '\'' +
                '}';
    }
}
