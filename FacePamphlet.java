
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	private JTextField name;
	private JButton add;
	private JButton delete;
	private JButton lookup;
	private JTextField status;
	private JButton changeStatus;
	private JTextField picture;
	private JButton changePicture;
	private JTextField friend;
	private JButton addFriend;
	private FacePamphletProfile currentProfile;
	private FacePamphletDatabase dataBase;
	private FacePamphletCanvas canvas;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */

	public void init() {
		JLabel label = new JLabel("Name");
		add(label, NORTH);
		name = new JTextField(TEXT_FIELD_SIZE);
		add = new JButton("Add");
		delete = new JButton("Delete");
		lookup = new JButton("Lookup");
		status = new JTextField(TEXT_FIELD_SIZE);
		changeStatus = new JButton("Change Status");
		picture = new JTextField(TEXT_FIELD_SIZE);
		changePicture = new JButton("Change Picture");
		friend = new JTextField(TEXT_FIELD_SIZE);
		addFriend = new JButton("Add Friend");
		JLabel emptyLabel1 = new JLabel(EMPTY_LABEL_TEXT);
		JLabel emptyLabel2 = new JLabel(EMPTY_LABEL_TEXT);
		add(name, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);
		add(status, WEST);
		status.addActionListener(this);
		add(changeStatus, WEST);
		add(emptyLabel1, WEST);
		add(picture, WEST);
		picture.addActionListener(this);
		add(changePicture, WEST);
		add(emptyLabel2, WEST);
		add(friend, WEST);
		friend.addActionListener(this);
		add(addFriend, WEST);
		addActionListeners();
		dataBase = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add && !name.getText().equals("")) {
			dataBase.addProfile(new FacePamphletProfile(name.getText()));
			currentProfile = dataBase.getProfile(name.getText());
			canvas.displayProfile(currentProfile);
			canvas.showMessage("New profile created");
			name.setText("");
		} else if (e.getSource() == delete && !name.getText().equals("")) {
			if (dataBase.containsProfile(name.getText())) {
				dataBase.deleteProfile(name.getText());
				currentProfile = null;
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Profile of " + name.getText() + " deleted");
			} else {
				canvas.showMessage("A profile with name " + name.getText() + " does not exist");
			}
			name.setText("");
		} else if (e.getSource() == lookup && !name.getText().equals("")) {
			if (dataBase.containsProfile(name.getText())) {
				currentProfile = dataBase.getProfile(name.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Displaying " + currentProfile.getName());
			} else {
				canvas.showMessage("A profile with name " + name.getText() + " does not exist");
			}
			name.setText("");
		} else if ((e.getSource() == picture || e.getSource() == changePicture) && !picture.getText().equals("")) {
			if (currentProfile != null) {
				try {
					currentProfile.setImage(new GImage(picture.getText()));
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Profile picture updated");
				} catch (Exception ex) {
					canvas.showMessage("There is no picture in the file with this name");
				}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}
			picture.setText("");
		} else if ((e.getSource() == status || e.getSource() == changeStatus) && !status.getText().equals("")) {
			if (currentProfile != null) {
				currentProfile.setStatus(status.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Status updated");
			} else {
				canvas.showMessage("Please select a profile to change status");
			}
			status.setText("");
		} else if ((e.getSource() == friend || e.getSource() == addFriend) && !friend.getText().equals("")) {
			if (currentProfile != null) {
				if (!friend.getText().equals(currentProfile.getName())) {
					if (dataBase.containsProfile(friend.getText())) {
						currentProfile.addFriend(friend.getText());
						dataBase.getProfile(friend.getText()).addFriend(currentProfile.getName());
						canvas.displayProfile(currentProfile);
						canvas.showMessage(friend.getText() + " added as a friend");
					} else {
						canvas.showMessage("A profile with name " + friend.getText() + " does not exist");
					}
				} else {
					canvas.showMessage("Please select a profile to add friend");
				}
				friend.setText("");
			}
		}
	}
}
