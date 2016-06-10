package com.miolfo.mapgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.miolfo.gamelogic.*;

public class MapGenGdx extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer shapeRenderer;
	GameMap map;

	//Map parameters
	int tileWidthPx, tileHeightPx, mapSize;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		MapFactory mf = new MapFactory().MapSize(80);
		map = mf.Generate();
		mapSize = map.GetSize();
		tileWidthPx = 640 / mapSize;
		tileHeightPx = 480 / mapSize;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 0, 0);
		renderMap();
		//shapeRenderer.begin();
		//shapeRenderer.rect(20,20, 20, 20);
		//shapeRenderer.rect(40,40, 20, 20);
		//shapeRenderer.end();
		//batch.end();
	}

	public void renderMap(){
		//Determine the size of single tile
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(int i = 0; i < mapSize; i++){
			for(int j = 0; j < mapSize; j++){
				Tile t = map.GetTile(i,j);
				switch(t){
					case TILE_DESERT:
						shapeRenderer.setColor(Color.CORAL);
						break;
					case TILE_FOREST:
						shapeRenderer.setColor(Color.FOREST);
						break;
					case TILE_GRASS:
						shapeRenderer.setColor(Color.GREEN);
						break;
					case TILE_SNOW:
						shapeRenderer.setColor(Color.WHITE);
					default:
						shapeRenderer.setColor(Color.WHITE);
						break;
				}
				shapeRenderer.rect(i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
			}
		}
		shapeRenderer.end();
	}
}
