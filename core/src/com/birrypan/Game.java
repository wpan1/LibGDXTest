package com.birrypan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Game extends ApplicationAdapter {
	public static final String ASSETS_PATH = "";
	public static final int RENDER_WIDTH = 640;
	public static final int RENDER_HEIGHT = 480;
	
	private SpriteBatch batch;
	private Player player;
	private PlayerSprite playerSprite;
	private Camera camera;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer  mapRenderer;
	private OrthographicCamera mapCamera;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load("map.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		mapCamera = new OrthographicCamera(RENDER_WIDTH,RENDER_HEIGHT);
		
		player = new Player(1326, 688.5, Angle.fromDegrees(0), (TiledMapTileLayer) map.getLayers().get(0));
	}
	
	@Override
	public void render() {
        // Get data about the current input (keyboard state).
        // Update the player's rotation and position based on key presses.
        double rotate_dir = 0;
        double move_dir = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            //move_dir -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            move_dir += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            rotate_dir += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            rotate_dir -= 1;
        
        BitmapFont font = new BitmapFont();
        if (Gdx.input.getAccelerometerY() >= 0.5 || Gdx.input.getAccelerometerY() <= 0.5)
        	rotate_dir -= Math.signum(Gdx.input.getAccelerometerY());
        else
        	rotate_dir = 0;
        move_dir -= Math.signum(Gdx.input.getAccelerometerZ() - 7);
        
        Orientation orientation = Gdx.input.getNativeOrientation();
        
		//update
		player.update(rotate_dir, move_dir);
        mapCamera.position.set(
        		(float) (player.getX()),
        		(float) (player.getY()),
        		0);
		mapCamera.update();
		mapRenderer.setView(mapCamera);
		
		//draw
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//tiled map draw
		
		mapRenderer.render();		
		//sprite draw
		batch.begin();
		//font.draw(batch,String.valueOf(Gdx.input.getAccelerometerZ()),10,10);
		//font.draw(batch,String.valueOf(Gdx.input.getAccelerometerY()),10,30);
		player.render(batch, mapCamera);
		batch.end();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		map.dispose();
		mapRenderer.dispose();
	}
}
