package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

import java.util.Comparator;

/**
 * Created by barry on 12/8/15 @ 9:49 PM.
 */
public class RenderingSystem extends IteratingSystem {

    private final float PPM;
    private final float FRUSTUM_WIDTH;
    private final float FRUSTUM_HEIGHT;

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;

    private Color tintPlaceholder = Color.WHITE.cpy();

    public RenderingSystem(SpriteBatch batch, float pixelsPerMeter) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());//, new ZComparator());
        PPM = pixelsPerMeter;

        FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
        FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;
        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<>();
        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int) Math.signum(transformM.get(entityB).position.z -
                        transformM.get(entityA).position.z);
            }
        };

        this.batch = batch;


        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);
            TransformComponent t = transformM.get(entity);

            if (tex.region == null || t.isHidden) {
                continue;
            }


            Color c = batch.getColor();
            tintPlaceholder.set(t.tint.r, t.tint.g, t.tint.b, t.tint.a);
            batch.setColor(tintPlaceholder);
            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float originX = width/2f;
            float originY = height/2f;

            batch.draw(tex.region,
                    t.position.x - originX, t.position.y - originY,
                    originX, originY,
                    width, height,
                    PixelsToMeters(t.scale.x), PixelsToMeters(t.scale.y),
                    t.rotation);
            batch.setColor(c);
        }

        batch.end();
        renderQueue.clear();
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }


    private float PixelsToMeters(float pixelValue){
        return pixelValue * (1.0f/PPM);
    }
}
