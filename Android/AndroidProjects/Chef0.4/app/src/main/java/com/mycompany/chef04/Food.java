package com.mycompany.chef04;

import java.util.ArrayList;

public class Food {
    private String foodname, foodtype, foodthumbnailUrl, chefname, chefemail, chefdesc, chefthumbnailUrl;
    private double chefphNo;
    private ArrayList<String> foodchar, locser;

    public Food(String foodname, String foodtype, String foodthumbnailUrl, ArrayList<String> foodchar,
                String chefname, String chefemail, String chefdesc, double chefphNo, String chefthumbnailUrl,
                ArrayList<String> locser){
        this.foodname=foodname;

        this.foodtype=foodtype;
        this.foodthumbnailUrl=foodthumbnailUrl;
        this.foodchar=foodchar;
        this.chefname=chefname;
        this.chefemail=chefemail;
        this.chefdesc=chefdesc;
        this.chefphNo=chefphNo;
        this.chefthumbnailUrl=chefthumbnailUrl;
        this.locser=locser;
    }

    public Food() {
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getFoodthumbnailUrl() {
        return foodthumbnailUrl;
    }

    public void setFoodthumbnailUrl(String foodthumbnailUrl) {
        this.foodthumbnailUrl = foodthumbnailUrl;
    }

    public String getChefname() {
        return chefname;
    }

    public void setChefname(String chefname) {
        this.chefname = chefname;
    }

    public String getChefemail() {
        return chefemail;
    }

    public void setChefemail(String chefemail) {
        this.chefemail = chefemail;
    }

    public String getChefdesc() {
        return chefdesc;
    }

    public void setChefdesc(String chefdesc) {
        this.chefdesc = chefdesc;
    }

    public String getChefthumbnailUrl() {
        return chefthumbnailUrl;
    }

    public void setChefthumbnailUrl(String chefthumbnailUrl) {
        this.chefthumbnailUrl = chefthumbnailUrl;
    }

    public double getChefphNo() {
        return chefphNo;
    }

    public void setChefphNo(double chefphNo) {
        this.chefphNo = chefphNo;
    }

    public ArrayList<String> getFoodchar() {
        return foodchar;
    }

    public void setFoodchar(ArrayList<String> foodchar) {
        this.foodchar = foodchar;
    }

    public ArrayList<String> getLocser() {
        return locser;
    }

    public void setLocser(ArrayList<String> locser) {
        this.locser = locser;
    }
}
