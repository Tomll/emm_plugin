package com.example.administrator.emmapplication.utils.enums;

public enum CMD {
    NULL("null",-1),
    RING("ring",1),LOC("location",2),LOCK("lock",3),
    Ers_Dvc("erase_device",4),Elmnt_Dvc("eliminate_device",5),MISS("mark_missing",6),
    BACK("mark_get_back",7),SCREEN("screen_shot",8),PHOTO("photo_graph",9);

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

    private String name;
    private int index;
    private CMD(String name, int index){
        this.name=name;
        this.index=index;
    }

    public static CMD getById(int index){
        for(CMD cmd : values()){
            if(cmd.getIndex() == index){
                return cmd;
            }
        }
        return NULL;
    }
}
