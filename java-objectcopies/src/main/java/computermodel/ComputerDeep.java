package computermodel;

public class ComputerDeep {

    private HardDrive hardDrive;
    private Cpu cpu;

    public ComputerDeep(HardDrive hardDrive, Cpu cpu) {
        this.hardDrive = new HardDrive(hardDrive.getModelNumber(), hardDrive.getBrandName());
        this.cpu = new Cpu(cpu.getModelNumber(), cpu.getBrandName());
    }

    public HardDrive getHardDrive() {
        return hardDrive;
    }

    public Cpu getCpu() {
        return cpu;
    }

}
