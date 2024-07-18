public class SystemConfig {

    private int id;
    private Setting setting;

    public SystemConfig(int id, Setting setting) {
        this.id = id;
        this.setting = setting;
    }

    public SystemConfig(SystemConfig systemConfig) {
        this.id = systemConfig.id;
        this.setting = systemConfig.setting;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public int getId() {
        return id;
    }

    public Setting getSetting() {
        return setting;
    }
}
