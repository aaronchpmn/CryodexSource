package cryodex.modules.l5r.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cryodex.Player;
import cryodex.modules.ModulePlayer;
import cryodex.modules.RegistrationPanel;
import cryodex.modules.l5r.L5RPlayer;

public class L5RRegistrationPanel implements RegistrationPanel {
	
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
			panel.add(new JLabel("<html><b>L5R</b></html>"), gbc);

			gbc.gridy++;
		}

		return null;
	}

	@Override
	public void save(Player player) {

		L5RPlayer xp = null;

		// get module information
		if (player.getModuleInfo() != null
				&& player.getModuleInfo().isEmpty() == false) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof L5RPlayer) {
					xp = (L5RPlayer) mp;
					break;
				}
			}
		}

		// if no module information, create one and add it to player
		if (xp == null) {
			xp = new L5RPlayer(player);
			player.getModuleInfo().add(xp);
		}
	}

	@Override
	public void load(Player player) {
		L5RPlayer xp = null;

		// get module information
		if (player != null && player.getModuleInfo() != null
				&& player.getModuleInfo().isEmpty() == false) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof L5RPlayer) {
					xp = (L5RPlayer) mp;
					break;
				}
			}
		}

		// if no module information, create one and add it to player
		if (xp != null) {
			// Currently nothing to load
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
