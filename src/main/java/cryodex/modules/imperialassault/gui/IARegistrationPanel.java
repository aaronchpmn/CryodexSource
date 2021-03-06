package cryodex.modules.imperialassault.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cryodex.Player;
import cryodex.modules.ModulePlayer;
import cryodex.modules.RegistrationPanel;
import cryodex.modules.imperialassault.IAPlayer;

public class IARegistrationPanel implements RegistrationPanel {

	private JPanel panel;

	@Override
	public JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.anchor = GridBagConstraints.EAST;
			panel.add(new JLabel("<html><b>Imperial Assault</b></html>"), gbc);

			gbc.gridy++;
		}

		return null;
	}

	@Override
	public void save(Player player) {

		IAPlayer xp = null;

		// get module information
		if (player.getModuleInfo() != null
				&& player.getModuleInfo().isEmpty() == false) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof IAPlayer) {
					xp = (IAPlayer) mp;
					break;
				}
			}
		}

		// if no module information, create one and add it to player
		if (xp == null) {
			xp = new IAPlayer(player);
			player.getModuleInfo().add(xp);
		}

		// update module information

	}

	@Override
	public void load(Player player) {
		IAPlayer xp = null;

		// get module information
		if (player != null && player.getModuleInfo() != null
				&& player.getModuleInfo().isEmpty() == false) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof IAPlayer) {
					xp = (IAPlayer) mp;
					break;
				}
			}
		}

		// if no module information, create one and add it to player
		if (xp != null) {
			//nothing currently
		} else {
			clearFields();
		}
	}

	@Override
	public void clearFields() {
	}


	@Override
	public void setVisible(boolean isVisible) {
		if(getPanel() != null){
			getPanel().setVisible(isVisible);
		}
	}
}
