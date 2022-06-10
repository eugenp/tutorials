public class ComputerShallow {

    private HardDrive hardDrive;
    private Cpu cpu;

    public ComputerShallow(HardDrive hardDrive, Cpu cpu) {
        this.hardDrive = hardDrive;
        this.cpu = cpu;
    }

    public HardDrive getHardDrive() {
        return hardDrive;
    }

    public Cpu getCpu() {
        return cpu;
    }

}
