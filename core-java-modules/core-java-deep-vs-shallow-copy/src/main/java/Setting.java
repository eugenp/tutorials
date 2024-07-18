public class Setting {

    private String name;
    private String value;

    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Setting(Setting setting) {
        this.name = setting.name;
        this.value = setting.value;
    }

    @Override
    public String toString() {
        return "Setting{name='" + name + "', value='" + value + "'}";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
