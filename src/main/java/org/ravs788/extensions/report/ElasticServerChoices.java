package org.ravs788.extensions.report;

public enum ElasticServerChoices {
    ON_CLOUD("ON_CLOUD"),
    ON_LOCALHOST_SECURE("ON_LOCALHOST_SECURE"),
    ON_LOCALHOST_INSECURE("ON_LOCALHOST_SECURE");

    ElasticServerChoices(String value){
        this.value = value;
    }

    private String value;

    public  String getValue(){
        return value;
    }
}
