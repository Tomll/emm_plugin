package com.example.administrator.emmapplication.utils.enums;

public enum STRTGY {
    NULL("null",-1),
    PWD("password",1);

    private String name;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private STRTGY(String name, int index){
        this.name=name;
        this.index=index;
    }

    public static STRTGY getById(int index){
        for(STRTGY strategy : values()){
            if(strategy.getIndex() == index){
                return strategy;
            }
        }
        return NULL;
    }
}
