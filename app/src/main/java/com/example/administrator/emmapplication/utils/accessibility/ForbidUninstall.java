package com.example.administrator.emmapplication.utils.accessibility;

public class ForbidUninstall {
    public static ForbidUninstall getInstance(){
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final ForbidUninstall INSTANCE = new ForbidUninstall();
    }
}
