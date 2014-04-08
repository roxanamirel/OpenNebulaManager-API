

package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class OpenNebulaConfigurationManager {

    private static Properties generalProperties;
    private static final String CONFIG_PATH="/var/lib/one/workspace/OpenNebulaManager-API/src/config/config.properties";
    
    static {
        generalProperties = new Properties();
        try {
            generalProperties.load(new FileInputStream(CONFIG_PATH));
        } catch (IOException e) {
            System.out.println("Unable to load CONFIG_PATH file");
            System.exit(1);
        }
    }
    public static String getOpenNebulaRCPAddress() {
        return generalProperties.getProperty("openNebulaRCPAPIAddress");
    }

    public static String getOpenNebulaCredentials() {
        return generalProperties.getProperty("openNebulaCredentials");
    }

	public static String getIMAGE_PATH_LOCATION() {
		 return generalProperties.getProperty("imagePathLocation");
	}
	public static String getDatastoreUsername(){
		return generalProperties.getProperty("datastoreUserName");
	}
	public static String getDatastorePassword(){
		return generalProperties.getProperty("datastorePassword");
	}
	public static String getDatastoreIp(){
		return generalProperties.getProperty("datastoreIp");
	}
}
