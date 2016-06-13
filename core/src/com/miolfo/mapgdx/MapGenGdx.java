package com.miolfo.mapgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.miolfo.gamelogic.*;

public class MapGenGdx extends Game {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	GameMap map;
	Player player;
	Texture grass_t, forest_t, snow_t, desert_t, player_t;

	//Map parameters
	int tileWidthPx, tileHeightPx, mapSize;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		grass_t = new Texture("grass64.png");
		forest_t = new Texture("forest64.png");
		snow_t = new Texture("snow64.png");
		desert_t = new Texture("desert64.png");
		player_t = new Texture("player64.png");
		MapFactory mf = new MapFactory().MapSize(32).NoOfForests(3).SizeOfForests(3);
		map = mf.Generate();
		mapSize = map.GetSize();
		//TODO: Dynamically get screen size
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		tileWidthPx =  w / mapSize;
		tileHeightPx = h / mapSize;

		player = new Player();
		player.SetTexture(player_t);
	}

	@Override
	public void dispose(){
		batch.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderMap();
		renderPlayer();
	}

	public void renderMap(){
		renderPartialMap(0, mapSize, 0, mapSize);
	}

	private void renderPartialMap(int xMin, int xMax, int yMin, int yMax){
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(int i = xMin; i < xMax; i++){
			for(int j = yMin; j < yMax; j++){
				Tile t = map.GetTile(i,j);
				batch.begin();
				switch(t){
					case TILE_DESERT:
						batch.draw(desert_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
						break;
					case TILE_FOREST:
						batch.draw(forest_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
						break;
					case TILE_GRASS:
						batch.draw(grass_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
						break;
					case TILE_SNOW:
						batch.draw(snow_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
						break;
					default:
						shapeRenderer.setColor(Color.WHITE);
						break;
				}
				batch.end();
			}
		}
		shapeRenderer.end();
	}

	private void renderPlayer(){
		batch.begin();
		batch.draw(player.GetTexture(), player.GetPosition().X() * tileWidthPx,
				player.GetPosition().Y() * tileHeightPx,
				tileWidthPx,
				tileHeightPx);
		batch.end();
	}
}
