package com.ajanda.bariskoc.ajanda.model;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by bariskoc on 25.09.2017.
 */

public class Gorev {

    private String id, konu, saat, tarih, ekleyen, detay, kategori, gun;
    private HashMap<String, Object> gorevTarihi;

    public HashMap<String, Object> getGorevTarihi() {
        return gorevTarihi;
    }

    public void setGorevTarihi(HashMap<String, Object> gorevTarihi) {
        this.gorevTarihi = gorevTarihi;
    }

    public Gorev(String id, String konu, String saat, String tarih, String ekleyen, String detay, String kategori, String gun, HashMap<String, Object> gorevTarihi) {
        this.id = id;
        this.konu = konu;
        this.saat = saat;
        this.tarih = tarih;
        this.ekleyen = ekleyen;
        this.detay = detay;
        this.kategori = kategori;
        this.gun = gun;
        this.gorevTarihi = gorevTarihi;
    }

    public Gorev(String id, String tarih, String gun) {
        this.id = id;
        this.tarih = tarih;
        this.gun = gun;
    }

    public Gorev(String id, String tarih) {
        this.id = id;
        this.tarih = tarih;
    }

    public Gorev() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getEkleyen() {
        return ekleyen;
    }

    public void setEkleyen(String ekleyen) {
        this.ekleyen = ekleyen;
    }

    public String getDetay() {
        return detay;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }
}
