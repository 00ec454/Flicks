package com.dharmesh.flicks.models;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    String id;
    String iso639v1;
    String iso3166v1;
    String key;
    String name;
    String site;
    int size;
    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("iso_639_1")
    public String getIso639v1() {
        return iso639v1;
    }

    @SerializedName("iso_3166_1")
    public void setIso639v1(String iso639v1) {
        this.iso639v1 = iso639v1;
    }

    public String getIso3166v1() {
        return iso3166v1;
    }

    public void setIso3166v1(String iso3166v1) {
        this.iso3166v1 = iso3166v1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
