package com.julia.demo2.Service;

import java.util.Collection;

import com.julia.demo2.Dao.FrameRepository;
import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import com.julia.demo2.Dao.FakeFrameDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

//    @Autowired
//    private FakeFrameDaoImpl fakeFrameDaoImpl;

    @Autowired
    private FrameRepository repo;

    private static final int maxNumberOfFrames = 10;

    public Collection<Frame> getAllFrames() {
        //return fakeFrameDaoImpl.getAllFrames();
        return repo.findAll();
        //return repo.getAllFrames();
    }

    public void roll(int pins) {
        if( pins < 0 || pins > 10 ) {
            throw new Error("number of pins is from the range <0; 10>");
        }

        Frame frame = null;

        for(int i=1; i<11;i++) {

            if(!repo.existsById(i)) {
                if(i!=maxNumberOfFrames) {
                    frame = new Frame(0,0,false, false);
                } else if (i == maxNumberOfFrames) {
                    frame = new LastFrame(0,0,0,false,false,false);
                }
                repo.save(frame);
            }

            frame = repo.getOne(i);

            if(frame.isRoll1Done()==false) {
                frame.setRoll1(pins);
                frame.setRoll1Done(true);
                break;
            }

            if(frame.isRoll2Done()==false) {
                frame.setRoll2(pins);
                frame.setRoll2Done(true);
                break;
            }

            if(frame instanceof LastFrame) {
                if( isGameFinished() ) {
                    throw new Error("End of game. Can't roll any more! " +
                            "Get score first, and than start a new game.");
                } else {
                    ((LastFrame) frame).setRoll3(pins);
                    ((LastFrame) frame).setRoll3Done(true);
                }
            }
        }
        repo.save(frame);
    }

    private boolean isGameFinished() {
        if (repo.existsById(maxNumberOfFrames)) {
            LastFrame lastFrame = (LastFrame) repo.getOne(maxNumberOfFrames);
            if (lastFrame.getRoll1() == 10 && lastFrame.isRoll2Done() && lastFrame.isRoll3Done() // strike
                    || lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() == 10 && lastFrame.isRoll3Done() // spare
                    || lastFrame.isRoll1Done() && lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() < 10) {
                return true;
            }
        }
        return false;
    }

    public int score() {
        return -1;
    }


    /*
    * gives total number of points at the END of the game
    * */
    public int old_score() {

        if( !isGameFinished() )
            throw new Error("Game is not finished yet. " +
                    "Score is available only after all rolls are done.");


        Collection<Frame> allFrames = getAllFrames();
        int score = 0, previousRoll1=0, previousRoll2=0;

        for( Frame frame : allFrames ) {

            int roll1 = frame.getRoll1(),
                roll2 = frame.getRoll2();

            score += roll1 + roll2;

            // bonus for previous rolls
            if(previousRoll1 == 10) { // strike
                score += previousRoll2 + roll1;
            } else if (previousRoll1 + previousRoll2 == 10) { // spare
                score += roll1;
            }


            if( frame instanceof LastFrame) {
                if(roll1 == 10) { // bonus for strike
                    score += roll2 + ((LastFrame) frame ).getRoll3();
                }
                if(roll1 + roll2 == 10) { // bonus for spare
                    score += ((LastFrame) frame ).getRoll3();
                }
            }

            previousRoll1 = roll1;
            previousRoll2 = roll2;

        }

        return score;
    }

//    private boolean old_isGameFinished() {
//        // LastFrame lastFrame = fakeFrameDaoImpl.getLastFrame();
//        LastFrame lastFrame = (LastFrame)repo.getOne(10); // repo.getLastFrame();
//        if( lastFrame == null ) {
//            return false;
//        }
//
//        if( lastFrame.getRoll1() == 10 && lastFrame.isRoll2Done() && lastFrame.isRoll3Done() // strike
//                || lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() == 10 && lastFrame.isRoll3Done() // spare
//                ||  lastFrame.isRoll1Done() && lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() < 10 ) {
//            return true;
//        }
//        return false;
//    }

//    public void old_roll( int pins ) {
//        if( pins < 0 || pins > 10 ) {
//            throw new Error("number of pins is from the range <0; 10>");
//        }
//
//        Collection<Frame> allFrames = getAllFrames();
//        for( Frame frame : allFrames ) {
//            if( frame.isRoll1Done() == false ) {
//                frame.setRoll1(pins);
//                frame.setRoll1Done(true);
//                break;
//            } else if ( frame.isRoll2Done() == false ) {
//                frame.setRoll2(pins);
//                frame.setRoll2Done(true);
//                break;
//            } else if ( frame instanceof LastFrame ) {
//                if( isGameFinished() ) {
//                    throw new Error("End of game. Can't roll any more! " +
//                            "Get score first, and than start a new game.");
//                } else {
//                    ((LastFrame) frame).setRoll3(pins);
//                    ((LastFrame) frame).setRoll3Done(true);
//                }
//            }
//        }
//    }



}

