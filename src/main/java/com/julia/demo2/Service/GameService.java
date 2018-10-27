package com.julia.demo2.Service;

import java.util.Collection;
import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import com.julia.demo2.Dao.FrameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private FrameDao frameDao;


    public Collection<Frame> getAllFrames() {
        return frameDao.getAllFrames();
    }

    public void roll( int pins ) {
        if( pins < 0 || pins > 10 ) {
            throw new Error("number of pins is from the range <0; 10>");
        }

        Collection<Frame> allFrames = getAllFrames();
        for( Frame frame : allFrames ) {
            if( frame.isRoll1Done() == false ) {
                frame.setRoll1(pins);
                frame.setRoll1Done(true);
                break;
            } else if ( frame.isRoll2Done() == false ) {
                frame.setRoll2(pins);
                frame.setRoll2Done(true);
                break;
            } else if ( frame instanceof LastFrame ) {
                if( isGameFinished() ) {
                    throw new Error("End of game. Can't roll any more! " +
                            "Get score first, and than start a new game.");
                } else {
                    ((LastFrame) frame).setRoll3(pins);
                    ((LastFrame) frame).setRoll3Done(true);
                }
            }
        }
    }

    public boolean isGameFinished() {
        LastFrame lastFrame = frameDao.getLastFrame();
        if( lastFrame == null ) {
            return false;
        }

        if( lastFrame.getRoll1() == 10 && lastFrame.isRoll2Done() && lastFrame.isRoll3Done() // strike
            || lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() == 10 && lastFrame.isRoll3Done() // spare
            ||  lastFrame.isRoll1Done() && lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() < 10 ) {
            return true;
        }
        return false;
    }

    /*
    * gives total number of points at the end of the game
    * */
    public int score() {

        if( !isGameFinished() ) {
            throw new Error("Game is not finished yet. " +
                    "Score is available only after all rolls are done.");
        }

        Collection<Frame> allFrames = getAllFrames();
        int score = 0, previousRoll1=0, previousRoll2=0;

        for( Frame frame : allFrames ) {

            int roll1 = frame.getRoll1(),
                roll2 = frame.getRoll2();

            score += roll1 + roll2;

            // bonus for previous rolls
            if(previousRoll1 == 10) { // strike
                score += roll1 + roll2;
            } else if (previousRoll1 + previousRoll2 == 10) { // spare
                score += roll1;
            }

            previousRoll1 = roll1;
            previousRoll2 = roll2;

            if( frame instanceof LastFrame) {
                score+= ((LastFrame) frame ).getRoll3();
            }
        }

        return score;
    }
}
