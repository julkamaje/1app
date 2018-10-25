package com.julia.demo2.Controller;

import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Service.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private FrameService frameService;

    //	http://localhost:8080/game/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Frame> getAllFrames() {
        return frameService.getAllFrames();
    }

    @RequestMapping(value = "/roll", method = RequestMethod.PUT)
    public void roll(int pins) {

    }

    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public int score() {
        return 1;
    }

}
