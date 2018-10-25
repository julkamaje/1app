package com.julia.demo2.Dao;

import com.julia.demo2.Entity.Frame;
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
                put(3, new Frame(4, 0, 0));
                put(3, new Frame(5, 0, 0));
                put(3, new Frame(6, 0, 0));
                put(3, new Frame(7, 0, 0));
                put(3, new Frame(8, 0, 0));
                put(3, new Frame(9, 0, 0));
                put(3, new Frame(10, 0, 0));
            }
        };
    }

    public Collection<Frame> getAllFrames() {
        return this.frames.values();
    }
}


























