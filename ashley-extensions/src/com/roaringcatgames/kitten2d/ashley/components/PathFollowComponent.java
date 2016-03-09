package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 2/29/16 @ 6:14 PM.
 */
public class PathFollowComponent  implements Component {

    public Vector2 point = new Vector2();
    public Path<Vector2> path;
    public boolean isFacingPath = false;
    public float baseRotation = 0f;
    /**
     * Seconds from beginning to end
     */
//    public float totalPathTime = 5f;
//    public float elapsedTime = 0f;

    public float speed = 5f;
    public float pathPosition = 0f;
    public boolean isPaused = false;

    public static PathFollowComponent create(){
        return new PathFollowComponent();
    }

    public PathFollowComponent setPath(Path<Vector2> path){
        this.path = path;
        return this;
    }


    public PathFollowComponent setSpeed(float spd){
        this.speed = spd;
        return this;
    }

    public PathFollowComponent setPathPosition(float position){
        this.pathPosition = position;
        return this;
    }
//    public PathFollowComponent setTotalPathTime(float time){
//        this.totalPathTime = time;
//        return this;
//    }

    public PathFollowComponent setPaused(boolean shouldPause){
        this.isPaused = shouldPause;
        return this;
    }

    public PathFollowComponent setFacingPath(boolean shouldFacePath){
        this.isFacingPath = shouldFacePath;
        return this;
    }

    public PathFollowComponent setBaseRotation(float baseRotation){
        this.baseRotation = baseRotation;
        return this;
    }

    /**
     * Useful to hack in a "start X% through the path"
     *  EX: pathComponent.setElapsedTime(0.5f); would set
     *      the path to start midway through.
     * @param elapsedTime - The new elapsed time
     * @return the PathFollowComponent instance being modified
     */
//    public PathFollowComponent setElapsedTime(float elapsedTime){
//        this.elapsedTime = elapsedTime;
//        return this;
//    }



}
