package com.julia.demo2.Service;

import java.util.Collection;

import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Dao.FrameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrameService {

    @Autowired
    private FrameDao frameDao;

    public Collection<Frame> getAllFrames() {
        return frameDao.getAllFrames();
    }
}
