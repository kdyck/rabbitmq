package com.qarepo.rabbitmq.model;


public class BannerPackage extends BannerHTML {

    private String packagePath;
    private String packageType;

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
}
