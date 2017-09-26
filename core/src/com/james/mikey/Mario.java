package com.james.mikey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Mike on 2017/9/19.
 */

public class Mario extends Actor {
    public static float x;
    public static float y;
    public float stateTime;

    Texture texture;
    TextureRegion currentFrame;

    ImageButton buttonL;
    ImageButton buttonR;
    Animation aniRight;
    Animation aniLeft;
    Animation aniIdle;

    STATE state;
    enum STATE {
        LEFT,RIGHT,IDLE
    }

    public Mario(float x, float y){
        this.x = x;
        this.y = y;
        this.stateTime = 0;
        this.show();
        state = STATE.IDLE;
    }

    public void show(){
        texture = new Texture(Gdx.files.internal("mario.png"));

        TextureRegion[][] spilt = TextureRegion.split(texture,64,64);
        TextureRegion[][] miror = TextureRegion.split(texture,64,64);

        for(TextureRegion[] region1 : miror){//镜面mario
            for(TextureRegion region2 : region1){
                region2.flip(true,false);
            }
        }
        //right
        TextureRegion[] regionR = new TextureRegion[3];
        regionR[0] = spilt[0][1];
        regionR[1] = spilt[0][2];
        regionR[2] = spilt[0][0];

        //left
        TextureRegion[] regionL = new TextureRegion[3];
        regionL[0] = miror[0][1];
        regionL[1] = miror[0][2];
        regionL[2] = miror[0][0];

        //idle
        TextureRegion[] regionI = new TextureRegion[1];
        regionI[0] = spilt[0][0];

        aniLeft = new Animation(1/10f,regionL);
        aniRight = new Animation(1/10f,regionR);
        aniIdle = new Animation(1/10f,regionI);

        //button
        buttonL = new ImageButton(new TextureRegionDrawable(spilt[1][0]),new TextureRegionDrawable(spilt[1][1]));
        buttonR = new ImageButton(new TextureRegionDrawable(miror[1][0]),new TextureRegionDrawable(miror[1][1]));
        buttonL.setPosition(20,20);
        buttonR.setPosition(400,20);

        buttonL.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                state = STATE.LEFT;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state = STATE.IDLE;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        buttonR.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                state = STATE.RIGHT;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state = STATE.IDLE;
                super.touchUp(event, x, y, pointer, button);
            }
        });

    }
    public void check(){
        if (state == STATE.LEFT){
            currentFrame = (TextureRegion) aniLeft.getKeyFrame( stateTime,true);
        }else if (state == STATE.RIGHT){
            currentFrame = (TextureRegion) aniRight.getKeyFrame(stateTime,true);
        }else if(state == STATE.IDLE){
            currentFrame = (TextureRegion) aniIdle.getKeyFrame(stateTime,true);
        }
    }
    public void update(){
        if(state == STATE.LEFT){
            this.x -= 1.5f;
            if(this.x < 20) this.x = 20;
        }else if(state == STATE.RIGHT){
            this.x += 1.5f;
            if (this.x > 400) this.x = 400;
        }
       // this.x = x;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        this.update();
        this.check();
        batch.draw(currentFrame,x,y);
    }
}





















