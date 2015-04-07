package controllers;

import views.MenuApplicationFrame;

/**
 *  THE PRIMARY CLASS FROM WHERE THE GAME STARTS
 */
public class Game {

    /**
     *  This method is where the game kick-starts.
     * @param args
     */
    public static void main(String[] args){
		//Begin the game by displaying the main menu
		new MenuApplicationFrame();
	}
}
