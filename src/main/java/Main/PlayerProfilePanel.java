package Main;

import javax.swing.*;
import java.awt.*;

public class PlayerProfilePanel extends JPanel {

    private JLabel playerNameLabel;
    private JLabel playerAvatarLabel;

    public PlayerProfilePanel(String playerName, ImageIcon playerAvatar) {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        playerNameLabel = new JLabel(playerName, SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        playerAvatarLabel = new JLabel(playerAvatar, SwingConstants.CENTER);

        add(playerAvatarLabel, BorderLayout.CENTER);
        add(playerNameLabel, BorderLayout.SOUTH);
    }

    public void setPlayerName(String playerName) {
        playerNameLabel.setText(playerName);
    }

    public void setPlayerAvatar(ImageIcon playerAvatar) {
        playerAvatarLabel.setIcon(playerAvatar);
    }
}
