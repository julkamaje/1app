package com.julia.demo2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    //	http://localhost:8080/game/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Frame> getAllFrames() {
        return gameService.getAllFrames();
    }

    //	http://localhost:8080/game/score/
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public int score() {
        return gameService.score();
    }

    // http://localhost:8080/game/roll/
    @RequestMapping(value = "/roll", method = RequestMethod.PUT)
    public void roll(@RequestBody String pins) {
        try {
            gameService.roll(Integer.parseInt(pins));
        } catch(Exception e) {
            e.getMessage();
        }
    }
}
