package utils;

public class Constants {

    public static final String CONFIGURATION_FILEPATH=System.getProperty("user.dir")+ "/src/main/resources/config.properties";
    //copy file path of config.properties and remove everything before src so tht code is dynamic
    //user.dir relates to each individual's computer
    public static final int IMPLICIT_WAIT=20;
    public static final int EXPLICIT_WAIT=20;
    public static final String TESTDATA_FILEPATH = System.getProperty("user.dir")+ "/src/test/resources/testdata/hrmsdatabatch10.xlsx";
    public static final String SCREENSHOT_FILEPATH = System.getProperty("user.dir")+ "/screenshots/";


}
