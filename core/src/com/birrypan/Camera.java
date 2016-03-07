package com.birrypan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/* SWEN20003 Object Oriented Software Development
 * Racing Kart Game
 * Matt Giuca
 */

/** The camera, a rectangle positioned in the world.
 */
public class Camera extends OrthographicCamera
{
    // In pixels
    private int left;
    private int top;

    // Accessors
    /** The left x coordinate of the camera (pixels). */
    public int getLeft()
    {
        return left;
    }
    /** The right x coordinate of the camera (pixels). */
    public int getRight()
    {
        return (int) (left + viewportWidth);
    }
    /** The top y coordinate of the camera (pixels). */
    public int getTop()
    {
        return top;
    }
    /** The bottom y coordinate of the camera (pixels). */
    public int getBottom()
    {
        return (int) (top + viewportHeight);
    }
    /** The width of the camera viewport (pixels). */
    public int getWidth()
    {
    	return (int) viewportWidth;
    }
    /** The height of the camera viewport (pixels). */
    public int getHeight()
    {
    	return (int) viewportHeight;
    }

    /** Creates a new Camera centered around the player.
     * @param width The width of the camera viewport (pixels).
     * @param height The height of the camera viewport (pixels).
     * @param player The player, to get the player's location.
     */
    public Camera(int width, int height, Player player)
    {
    	this.viewportHeight = height;
    	this.viewportWidth = width;
        follow(player);
    }

    /** Move the camera such that the given player is centered.
     * @param player The player, to get the player's location.
     */
    public void follow(Player player)
    {
    	int left = (int) (player.getX() - (Gdx.graphics.getWidth() / 2));
    	int top = (int) (player.getY() + (Gdx.graphics.getHeight() / 2));
        moveTo(left, top);
        //lookAt(100,220, 0);
        setToOrtho(false,viewportWidth,viewportHeight);
        update();
    }

	/** Update the camera's x and y coordinates.
     * @param left New left x coordinate of the camera (pixels).
     * @param top New top y coordinate of the camera (pixels).
     */
    public void moveTo(int left, int top)
    {
        this.left = left;
        this.top = top;
    }
}
