package com.julia.demo2.Dao;

import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface FrameDao  {

    Collection<Frame> getAllFrames();

    LastFrame getLastFrame();
}
