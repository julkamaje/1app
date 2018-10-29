package com.julia.demo2;

import com.julia.demo2.Dao.FrameRepository;
import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import com.julia.demo2.Service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Main.class
)
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FrameRepository repo;

    @Autowired
    GameService gameService;

    private static final int maxNumberOfFrames = 10;

    @Before
    public void init() {
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new Frame( 1, 1 ) );
        repo.save( new LastFrame(1,1,0, true, true, false) );
    }

    private void previewFrames() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/game/")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println("------------------ previewFrames ------------------");
        System.out.println("response content = " + mvcResult.getResponse().getContentAsString());
    }

    private int getScore() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/game/score/")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        return Integer.parseInt(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void scoreNotAvailableBeforeGameHasFinished() throws Exception {
        Error e = null;
        try{
            LastFrame lastFrame = (LastFrame) repo.findById(maxNumberOfFrames).orElse(null);
            lastFrame.setRoll3Done(false);
            lastFrame.setRoll2(0);
            lastFrame.setRoll2Done(false);

            previewFrames();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            assertEquals( ex.getMessage(), gameService.GAME_NOT_FINISHED );
        }
    }

    @Test
    public void scoreWithStrikeBonusAndSpareBonusInLastRound() {
        try{

            Frame frame = repo.findById(1).orElseGet(null);
            frame.setRoll1(10);

            LastFrame lastFrame =  (LastFrame) repo.findById(maxNumberOfFrames).orElseGet(null);
            lastFrame.setRoll1(5);
            lastFrame.setRoll2(5);
            lastFrame.setRoll3(5);
            lastFrame.setRoll3Done(true);

            repo.save(frame);
            repo.save(lastFrame);

            previewFrames();
            int score = getScore();

            assertEquals(44, score); // 37 normal points + 2 strike bonus + 5 spare bonus

        } catch (Exception e) {
            System.out.println("error message = " + e.getMessage());
        }
    }
}
