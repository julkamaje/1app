package com.julia.demo2.Controller;

import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService frameService;

    //	http://localhost:8080/game/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Frame> getAllFrames() {
        return frameService.getAllFrames();
    }

    //	http://localhost:8080/game/score/
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public int score() {
        return frameService.score();
    }

    //	http://localhost:8080/game/roll?pins=1 <-- nie dziala
    // http://localhost:8080/game/roll
    @RequestMapping(value = "/roll", method = RequestMethod.PUT) //consumes=MediaType.APPLICATION_JSON_VALUE)
    public void roll(@RequestBody String pins) {
        try {
            frameService.roll(Integer.parseInt(pins));
        } catch(Exception e) {
            e.getMessage();
        }
    }

    //	http://localhost:8080/game/roll?pinsNumber=1
//    @RequestMapping(value = "/roll", method = RequestMethod.PUT)
//    public void roll(@RequestParam(value="pinsNumber") String pins) {
//        //frameService.roll(pins);
//    }
}
