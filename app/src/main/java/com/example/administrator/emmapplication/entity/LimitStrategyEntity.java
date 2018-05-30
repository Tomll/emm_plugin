package com.example.administrator.emmapplication.entity;

import java.util.List;

public class LimitStrategyEntity {

    /**
     * policy_id : 0
     * policy_name :
     * policy_desc :
     * password : {"pwLength":4,"pwComplexity":0,"minChars":1,"minLowerChars":0,"minUpperChars":0,"minNonalphabetic":0,"minDigital":1,"minSymbols":1,"maxFailedAttempts":-1,"maxPinageInDays":0,"pinHistory":0,"maxInactivity":0,"configFlag":0}
     * restriction : {"allowCamera":1,"allowSdCard":1,"allowWifi":1,"allowBluetooth":1,"allowCellular":1,"allowMobileHotspot":1,"allow_gps":-1,"allowDateTime":1,"allowUnknownSources":1,"allowSafeMode":1,"allowADB":1,"allowMTP":1,"allowRecord":1,"allowScreenshots":1,"allowWipe":1,"forbitAppList":[],"whiteList":[],"configFlag":0}
     * encrytion : {"storageFlag":1,"configFlag":0}
     */

    private int policy_id;
    private String policy_name;
    private String policy_desc;
    private PasswordBean password;
    private RestrictionBean restriction;
    private EncrytionBean encrytion;

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getPolicy_desc() {
        return policy_desc;
    }

    public void setPolicy_desc(String policy_desc) {
        this.policy_desc = policy_desc;
    }

    public PasswordBean getPassword() {
        return password;
    }

    public void setPassword(PasswordBean password) {
        this.password = password;
    }

    public RestrictionBean getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionBean restriction) {
        this.restriction = restriction;
    }

    public EncrytionBean getEncrytion() {
        return encrytion;
    }

    public void setEncrytion(EncrytionBean encrytion) {
        this.encrytion = encrytion;
    }

    public static class PasswordBean {
        /**
         * pwLength : 4
         * pwComplexity : 0
         * minChars : 1
         * minLowerChars : 0
         * minUpperChars : 0
         * minNonalphabetic : 0
         * minDigital : 1
         * minSymbols : 1
         * maxFailedAttempts : -1
         * maxPinageInDays : 0
         * pinHistory : 0
         * maxInactivity : 0
         * configFlag : 0
         */

        private int pwLength;
        private int pwComplexity;
        private int minChars;
        private int minLowerChars;
        private int minUpperChars;
        private int minNonalphabetic;
        private int minDigital;
        private int minSymbols;
        private int maxFailedAttempts;
        private int maxPinageInDays;
        private int pinHistory;
        private int maxInactivity;
        private int configFlag;

        public int getPwLength() {
            return pwLength;
        }

        public void setPwLength(int pwLength) {
            this.pwLength = pwLength;
        }

        public int getPwComplexity() {
            return pwComplexity;
        }

        public void setPwComplexity(int pwComplexity) {
            this.pwComplexity = pwComplexity;
        }

        public int getMinChars() {
            return minChars;
        }

        public void setMinChars(int minChars) {
            this.minChars = minChars;
        }

        public int getMinLowerChars() {
            return minLowerChars;
        }

        public void setMinLowerChars(int minLowerChars) {
            this.minLowerChars = minLowerChars;
        }

        public int getMinUpperChars() {
            return minUpperChars;
        }

        public void setMinUpperChars(int minUpperChars) {
            this.minUpperChars = minUpperChars;
        }

        public int getMinNonalphabetic() {
            return minNonalphabetic;
        }

        public void setMinNonalphabetic(int minNonalphabetic) {
            this.minNonalphabetic = minNonalphabetic;
        }

        public int getMinDigital() {
            return minDigital;
        }

        public void setMinDigital(int minDigital) {
            this.minDigital = minDigital;
        }

        public int getMinSymbols() {
            return minSymbols;
        }

        public void setMinSymbols(int minSymbols) {
            this.minSymbols = minSymbols;
        }

        public int getMaxFailedAttempts() {
            return maxFailedAttempts;
        }

        public void setMaxFailedAttempts(int maxFailedAttempts) {
            this.maxFailedAttempts = maxFailedAttempts;
        }

        public int getMaxPinageInDays() {
            return maxPinageInDays;
        }

        public void setMaxPinageInDays(int maxPinageInDays) {
            this.maxPinageInDays = maxPinageInDays;
        }

        public int getPinHistory() {
            return pinHistory;
        }

        public void setPinHistory(int pinHistory) {
            this.pinHistory = pinHistory;
        }

        public int getMaxInactivity() {
            return maxInactivity;
        }

        public void setMaxInactivity(int maxInactivity) {
            this.maxInactivity = maxInactivity;
        }

        public int getConfigFlag() {
            return configFlag;
        }

        public void setConfigFlag(int configFlag) {
            this.configFlag = configFlag;
        }
    }

    public static class RestrictionBean {
        /**
         * allowCamera : 1
         * allowSdCard : 1
         * allowWifi : 1
         * allowBluetooth : 1
         * allowCellular : 1
         * allowMobileHotspot : 1
         * allow_gps : -1
         * allowDateTime : 1
         * allowUnknownSources : 1
         * allowSafeMode : 1
         * allowADB : 1
         * allowMTP : 1
         * allowRecord : 1
         * allowScreenshots : 1
         * allowWipe : 1
         * forbitAppList : []
         * whiteList : []
         * configFlag : 0
         */

        private int allowCamera;
        private int allowSdCard;
        private int allowWifi;
        private int allowBluetooth;
        private int allowCellular;
        private int allowMobileHotspot;
        private int allow_gps;
        private int allowDateTime;
        private int allowUnknownSources;
        private int allowSafeMode;
        private int allowADB;
        private int allowMTP;
        private int allowRecord;
        private int allowScreenshots;
        private int allowWipe;
        private int configFlag;
        private List<?> forbitAppList;
        private List<?> whiteList;

        public int getAllowCamera() {
            return allowCamera;
        }

        public void setAllowCamera(int allowCamera) {
            this.allowCamera = allowCamera;
        }

        public int getAllowSdCard() {
            return allowSdCard;
        }

        public void setAllowSdCard(int allowSdCard) {
            this.allowSdCard = allowSdCard;
        }

        public int getAllowWifi() {
            return allowWifi;
        }

        public void setAllowWifi(int allowWifi) {
            this.allowWifi = allowWifi;
        }

        public int getAllowBluetooth() {
            return allowBluetooth;
        }

        public void setAllowBluetooth(int allowBluetooth) {
            this.allowBluetooth = allowBluetooth;
        }

        public int getAllowCellular() {
            return allowCellular;
        }

        public void setAllowCellular(int allowCellular) {
            this.allowCellular = allowCellular;
        }

        public int getAllowMobileHotspot() {
            return allowMobileHotspot;
        }

        public void setAllowMobileHotspot(int allowMobileHotspot) {
            this.allowMobileHotspot = allowMobileHotspot;
        }

        public int getAllow_gps() {
            return allow_gps;
        }

        public void setAllow_gps(int allow_gps) {
            this.allow_gps = allow_gps;
        }

        public int getAllowDateTime() {
            return allowDateTime;
        }

        public void setAllowDateTime(int allowDateTime) {
            this.allowDateTime = allowDateTime;
        }

        public int getAllowUnknownSources() {
            return allowUnknownSources;
        }

        public void setAllowUnknownSources(int allowUnknownSources) {
            this.allowUnknownSources = allowUnknownSources;
        }

        public int getAllowSafeMode() {
            return allowSafeMode;
        }

        public void setAllowSafeMode(int allowSafeMode) {
            this.allowSafeMode = allowSafeMode;
        }

        public int getAllowADB() {
            return allowADB;
        }

        public void setAllowADB(int allowADB) {
            this.allowADB = allowADB;
        }

        public int getAllowMTP() {
            return allowMTP;
        }

        public void setAllowMTP(int allowMTP) {
            this.allowMTP = allowMTP;
        }

        public int getAllowRecord() {
            return allowRecord;
        }

        public void setAllowRecord(int allowRecord) {
            this.allowRecord = allowRecord;
        }

        public int getAllowScreenshots() {
            return allowScreenshots;
        }

        public void setAllowScreenshots(int allowScreenshots) {
            this.allowScreenshots = allowScreenshots;
        }

        public int getAllowWipe() {
            return allowWipe;
        }

        public void setAllowWipe(int allowWipe) {
            this.allowWipe = allowWipe;
        }

        public int getConfigFlag() {
            return configFlag;
        }

        public void setConfigFlag(int configFlag) {
            this.configFlag = configFlag;
        }

        public List<?> getForbitAppList() {
            return forbitAppList;
        }

        public void setForbitAppList(List<?> forbitAppList) {
            this.forbitAppList = forbitAppList;
        }

        public List<?> getWhiteList() {
            return whiteList;
        }

        public void setWhiteList(List<?> whiteList) {
            this.whiteList = whiteList;
        }
    }

    public static class EncrytionBean {
        /**
         * storageFlag : 1
         * configFlag : 0
         */

        private int storageFlag;
        private int configFlag;

        public int getStorageFlag() {
            return storageFlag;
        }

        public void setStorageFlag(int storageFlag) {
            this.storageFlag = storageFlag;
        }

        public int getConfigFlag() {
            return configFlag;
        }

        public void setConfigFlag(int configFlag) {
            this.configFlag = configFlag;
        }
    }
}
