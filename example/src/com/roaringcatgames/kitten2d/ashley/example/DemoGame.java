package com.roaringcatgames.kitten2d.ashley.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.roaringcatgames.kitten2d.ashley.example.screens.HomeScreen;
import com.roaringcatgames.kitten2d.ashley.example.screens.OriginScreen;
import com.roaringcatgames.kitten2d.ashley.example.screens.TweenScreen;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Created by barry on 5/3/16 @ 6:33 PM.
 */
public class DemoGame extends Game implements IGameProcessor{

    public static final float PPM = 32f;
    private static final float VIEWPORT_SIZE = 640f/PPM;

    private InputMultiplexer multiplexer = new InputMultiplexer();
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private OrthographicCamera guiCam;
    private Viewport viewport;

    private HomeScreen home;
    private TweenScreen tween;
    private OriginScreen origin;
    @Override
    public void create () {
        batch = new SpriteBatch();
        //TODO: Camera System
        cam = new OrthographicCamera(VIEWPORT_SIZE, VIEWPORT_SIZE);
        viewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE, cam);
        viewport.apply();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(cam.viewportWidth/2f, cam.viewportHeight/2f, 0);

        guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        home = new HomeScreen(this);
        tween = new TweenScreen(this);
        origin = new OriginScreen(this);
        setScreen(home);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render () {
        float r = 29/255f;
        float g = 29/255f;
        float b = 27/255f;
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if(viewport != null) {
            this.viewport.update(width, height);
        }
    }

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Kitten2d Ashley Extensions Example";
        config.height = 640;
        config.width = 640;
        config.samples = 4;

        new LwjglApplication(new DemoGame(), config);
    }

    @Override
    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void switchScreens(String screenName) {
        if("HOME".equals(screenName)){
            setScreen(home);
        }else if("LEVEL_1".equals(screenName)){
            setScreen(tween);
        }else if("LEVEL_2".equals(screenName)) {
            setScreen(origin);
        }
    }

    @Override
    public void addInputProcessor(InputProcessor processor) {
        multiplexer.addProcessor(processor);
    }

    @Override
    public void removeInputProcessor(InputProcessor processor) {
        multiplexer.removeProcessor(processor);
    }

    @Override
    public OrthographicCamera getCamera() {
        return cam;
    }

    @Override
    public OrthographicCamera getGUICamera() {
        return guiCam;
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }
}
