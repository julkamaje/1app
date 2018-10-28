package com.julia.demo2.Dao;

import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FrameRepository extends JpaRepository<Frame, Integer> {

//    Collection<Frame> getAllFrames();
//
//    LastFrame getLastFrame();
}
