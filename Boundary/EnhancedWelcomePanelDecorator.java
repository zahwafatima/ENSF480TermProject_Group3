package Boundary;

import javax.swing.*;
import java.awt.*;

public class EnhancedWelcomePanelDecorator implements WelcomePanelDecorator {
    private WelcomePanel welcomePanel;

    public EnhancedWelcomePanelDecorator(WelcomePanel welcomePanel) {
        this.welcomePanel = welcomePanel;
    }

    @Override
    public JPanel createPanel() {
        return welcomePanel.createPanel();
    }

    @Override
    public JPanel decoratePanel() {
        JPanel decoratedPanel = new JPanel();
        decoratedPanel.setLayout(new BorderLayout());

        // Add additional decoration, e.g., a border
        decoratedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add the original welcome panel
        decoratedPanel.add(welcomePanel.createPanel(), BorderLayout.CENTER);

        return decoratedPanel;
    }
}
