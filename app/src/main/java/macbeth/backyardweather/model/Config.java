package macbeth.backyardweather.model;

public class Config {

    private static Config instance = null;

    private boolean tempDegC;
    private String email;
    private String password;

    private Config() {
        tempDegC = false;
        email = "";
        password = "";
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public void load() {

    }

    public void save() {

    }

    public boolean isTempDegC() {
        return tempDegC;
    }

    public void setTempDegC(boolean tempDegC) {
        this.tempDegC = tempDegC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
