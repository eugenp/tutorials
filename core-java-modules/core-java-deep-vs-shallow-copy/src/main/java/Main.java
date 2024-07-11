public class Main {

    public static void main(String[] args) {
        Setting initialSetting = new Setting("MaxConnections", "120");
        SystemConfig config1 = new SystemConfig(1, initialSetting);

        //        Shallow copy
        SystemConfig config2 = config1;
        config2.setId(2);
        config2.setSetting(new Setting("Timeout", "20s"));

        //        Deep copy
        SystemConfig config3 = new SystemConfig(config1);
        config3.setId(3);
        config3.setSetting(new Setting("MemoryLimit", "2GB"));

        System.out.println("Original Config ID: " + config1.getId() + "\nOriginal Config Setting: " + config1.getSetting());
        System.out.println("Shallow Copy Config ID: " + config2.getId() + "\nShallow copy Config Setting: " + config2.getSetting());
        System.out.println("Deep Copy Config ID: " + config3.getId() + "\nDeep copy Config Setting: " + config3.getSetting());
    }
}
