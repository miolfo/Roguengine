package com.miolfo.mapgdx;

import com.badlogic.gdx.ApplicationAdapter;
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
	Texture grass, forest, snow, desert;

	//Map parameters
	int tileWidthPx, tileHeightPx, mapSize;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		grass = new Texture("grass64.png");
		forest = new Texture("forest64.png");
		snow = new Texture("snow64.png");
		desert = new Texture("desert64.png");
		MapFactory mf = new MapFactory().MapSize(80).NoOfForests(10).SizeOfForests(10);
		map = mf.Generate();
		mapSize = map.GetSize();
		//TODO: Dynamically get screen size
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		tileWidthPx =  w / mapSize;
		tileHeightPx = h / mapSize;
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
	}

	public void renderMap(){
		//Determine the size of single tile
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(int i = 0; i < mapSize; i++){
			for(int j = 0; j < mapSize; j++){
				Tile t = map.GetTile(i,j);
				batch.begin();
				switch(t){
					case TILE_DESERT:
						batch.draw(desert, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
						break;
					case TILE_FOREST:
						batch.draw(forest, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
						break;
					case TILE_GRASS:
						batch.draw(grass, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
						break;
					case TILE_SNOW:
						batch.draw(snow, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
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
}
