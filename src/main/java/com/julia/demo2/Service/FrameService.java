package com.julia.demo2.Service;

import java.util.Collection;
import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
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

    public void roll(int pins) {
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
                LastFrame lastFrame = (LastFrame) frame;

                if( lastFrame.getRoll1() + lastFrame.getRoll2() != 10 || lastFrame.isRoll3Done() == true ) {
                    throw new Error("End of game. Can't roll any more! " +
                            "Get score first, and than start a new game.");
                } else {
                    ((LastFrame) frame).setRoll3(pins);
                    ((LastFrame) frame).setRoll3Done(true);
                }
            }
        }
    }

    public int score() {

        Collection<Frame> allFrames = getAllFrames();
        int score = 0, previousRoll1=0, previousRoll2=0;
        boolean allRollsDone = false;

        for( Frame frame : allFrames ) {

            int roll1 = frame.getRoll1();
            int roll2 = frame.getRoll2();

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
                allRollsDone = ((LastFrame) frame ).isRoll3Done();
            }
        }

        if(allRollsDone) return score;
        return -1;

    }
}
