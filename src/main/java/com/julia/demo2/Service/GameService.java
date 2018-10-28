package com.julia.demo2.Service;

import java.util.Collection;

import com.julia.demo2.Dao.FrameRepository;
import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private FrameRepository repo;

    private static final int maxNumberOfFrames = 10;

    public Collection<Frame> getAllFrames() {
        return repo.findAll();
    }

    public void roll(int pins) {

        if( isGameFinished() )
            throw new Error("End of game. Can't roll any more! " +
                    "Get score first, and than start a new game.");

        if( pins < 0 || pins > 10 )
            throw new Error("Number of pins is from the range <0; 10>");

        Frame frame = null;

        for(int i = 1; i <= maxNumberOfFrames; i++) {

            if(!repo.existsById(i)) { // frame does not exist

                if(i != maxNumberOfFrames) {
                    frame = new Frame(0,0,false, false);
                } else if (i == maxNumberOfFrames) {
                    frame = new LastFrame(0,0,0,false,false,false);
                }
                repo.save(frame);

            } else { // frame with id = i already exists in db

                if(i == maxNumberOfFrames) {
                    frame = repo.findById(maxNumberOfFrames).orElse(null); //findOne();
                } else {
                    frame = repo.findById(i).orElse(null);
                }
            }

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

            if(frame.getId() == maxNumberOfFrames) {
                ((LastFrame) frame).setRoll3(pins);
                ((LastFrame) frame).setRoll3Done(true);
            }
        }

        if(frame.getId() == maxNumberOfFrames) {
            repo.save((LastFrame)frame);
        } else {
            repo.save(frame);
        }
    }

    private boolean isGameFinished() {
        if (repo.existsById(maxNumberOfFrames)) {
            LastFrame lastFrame = (LastFrame) repo.findById(maxNumberOfFrames).orElse(null);
            if (lastFrame.getRoll1() == 10 && lastFrame.isRoll2Done() && lastFrame.isRoll3Done() // strike
                    || lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() == 10 && lastFrame.isRoll3Done() // spare
                    || lastFrame.isRoll1Done() && lastFrame.isRoll2Done() && lastFrame.getRoll1() + lastFrame.getRoll2() < 10) {
                return true;
            }
        }
        return false;
    }

    /*
     * gives total number of points at the END of the game
     * */
    public int score() {

        if( !isGameFinished() )
            throw new Error("Game is not finished yet. " +
                    "Score is available only after all rolls are done.");

        Collection<Frame> allFrames = getAllFrames();
        int score = 0, previousRoll1 = 0, previousRoll2 = 0;

        for( Frame frame : allFrames ) {

            int roll1 = frame.getRoll1(), roll2 = frame.getRoll2();

            score += roll1 + roll2;

            if(previousRoll1 == 10) score += previousRoll2 + roll1; // strike bonus
            if (previousRoll1 + previousRoll2 == 10 && frame.isRoll2Done()) score += roll1; // spare bonus

            if( frame instanceof LastFrame) {
                if(roll1 == 10) score += roll2 + ((LastFrame) frame ).getRoll3(); // strike bonus
                if(roll1 + roll2 == 10 && frame.isRoll2Done()) score += ((LastFrame) frame ).getRoll3(); // spare bonus
            }
            previousRoll1 = roll1;
            previousRoll2 = roll2;
        }
        return score;
    }
}

