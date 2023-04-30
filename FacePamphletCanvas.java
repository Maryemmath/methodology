
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	private GLabel message;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		message = new GLabel("");
		message.setFont(MESSAGE_FONT);
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		message.setLabel(msg);
		add(message, getWidth() / 2 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		double y = addProfileName(profile.getName());
		addProfileFriends(y, profile.getFriends());
		y = addProfileImage(y, profile.getImage());
		String status = "No current status";
		if (!profile.getStatus().equals("")) {
			status = profile.getName() + " is " + profile.getStatus();
		}
		addProfileStatus(y, status);
	}

	// adding profile friends on canvas
	private void addProfileFriends(double y, Iterator<String> friends) {
		GLabel friendLabel = new GLabel("Friends:");
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendLabel, getWidth() / 2, y + friendLabel.getHeight());
		y = friendLabel.getY();
		while(friends.hasNext()) {
			GLabel friend = new GLabel(friends.next());
			y+=friend.getHeight();
			friend.setFont(PROFILE_FRIEND_LABEL_FONT);
			add(friend, getWidth() / 2, y);
		}
	}

	// adding profile status on canvas
	private void addProfileStatus(double y, String status) {
		GLabel profileStatus = new GLabel(status);
		profileStatus.setFont(PROFILE_STATUS_FONT);
		add(profileStatus, LEFT_MARGIN, y + STATUS_MARGIN + profileStatus.getHeight());
	}

	// adding profile image on canvas
	private double addProfileImage(double y, GImage image) {
		if (image == null) {
			GRect emptyImage = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(emptyImage, LEFT_MARGIN, y + IMAGE_MARGIN);
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			add(noImage, emptyImage.getX() + emptyImage.getWidth() / 2 - noImage.getWidth() / 2,
					emptyImage.getY() + emptyImage.getHeight() / 2 + noImage.getHeight() / 2);
		} else {
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, LEFT_MARGIN, y + IMAGE_MARGIN);
		}
		return y + IMAGE_MARGIN + IMAGE_HEIGHT;

	}

	// adding profile name on canvas
	private double addProfileName(String name) {
		GLabel label = new GLabel(name);
		label.setFont(PROFILE_NAME_FONT);
		label.setColor(Color.BLUE);
		add(label, LEFT_MARGIN, TOP_MARGIN + label.getHeight());
		return label.getY();
	}

}
