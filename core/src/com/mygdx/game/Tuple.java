package com.mygdx.game;

/**
 * Created by linux on 3/26/18.
 */

public class Tuple<T,E> {
    T state;
    E action;
    public Tuple(T state, E action){
        this.state = state;
        this.action = action;


    }
    public T getState(){
        return this.state;
    }

    public E getAction(){
        return this.action;
    }

}
