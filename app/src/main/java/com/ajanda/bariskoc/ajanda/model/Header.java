package com.ajanda.bariskoc.ajanda.model;

/**
 * Created by bariskoc on 2.10.2017.
 */

public class Header {
    private String gun, tarih;

    public Header(String gun, String tarih) {
        this.gun = gun;
        this.tarih = tarih;
    }

    public Header() {
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
