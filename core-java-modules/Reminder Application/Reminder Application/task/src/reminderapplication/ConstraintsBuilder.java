package reminderapplication;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ConstraintsBuilder {

    static GridBagConstraints constraint(int x, int y) {
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        return gridBagConstraints;
    }
}
