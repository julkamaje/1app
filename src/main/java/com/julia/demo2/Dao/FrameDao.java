package com.julia.demo2.Dao;

import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

@Repository
public class FrameDao {

    private static Map<Integer, Frame> frames;

    static {
        frames = new HashMap<Integer, Frame>() {
            {
                put(1, new Frame(1, 0, 0));
                put(2, new Frame(2, 0, 0));
                put(3, new Frame(3, 0, 0));
                put(4, new Frame(4, 0, 0));
                put(5, new Frame(5, 0, 0));
                put(6, new Frame(7, 0, 0));
                put(7, new Frame(8, 0, 0));
                put(8, new Frame(9, 0, 0));
                put(10, new LastFrame(10, 0, 0, 0));
            }
        };
    }

    public Collection<Frame> getAllFrames() {
        return this.frames.values();
    }

    public LastFrame getLastFrame() {
        return (LastFrame) frames.get(10);
    }

    public void saveAllFrames(Collection<Frame> allFrames) {
        this.frames = ( Map<Integer, Frame> )allFrames;
    }
}


























