package createcopies;

import computermodel.ComputerDeep;
import computermodel.ComputerShallow;
import computermodel.Cpu;
import computermodel.HardDrive;

public class CreateCopies {

    public static ComputerDeep createDeepCopy() {

        HardDrive oldHardDrive = new HardDrive("1234", "Samsung");
        Cpu oldCpu = new Cpu("5678", "Intel");

        ComputerDeep oldComputer = new ComputerDeep(oldHardDrive, oldCpu);
        ComputerDeep newComputer = new ComputerDeep(oldComputer.getHardDrive(), oldComputer.getCpu());

        String updateOldBrandName = "AMD";
        oldCpu.setBrandName(updateOldBrandName);

        return newComputer;
    }

    public static ComputerShallow createShallowCopy() {

        HardDrive oldHardDrive = new HardDrive("1234", "Samsung");
        Cpu oldCpu = new Cpu("5678", "Intel");

        ComputerShallow oldComputer = new ComputerShallow(oldHardDrive, oldCpu);
        ComputerShallow newComputer = new ComputerShallow(oldComputer.getHardDrive(), oldComputer.getCpu());

        String updateOldBrandName = "AMD";
        oldCpu.setBrandName(updateOldBrandName);

        return newComputer;
    }
}
