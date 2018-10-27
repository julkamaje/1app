package com.julia.demo2;


import com.julia.demo2.Dao.FrameDao;
import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import com.julia.demo2.Service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameServiceTests {

	@Autowired
	private GameService gameService = new GameService();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FrameDao frameDao;

	private final String gameNotFinishedError = "Game is not finished yet. Score is available only after all rolls are done.";

	private Map<Integer, Frame> mockFrames = new HashMap<Integer, Frame>() {
		{
			put(1, new Frame(1, 0, 0));
			put(2, new Frame(2, 0, 0));
			put(3, new Frame(3, 0, 0));
			put(4, new Frame(4, 0, 0));
			put(5, new Frame(5, 0, 0));
			put(6, new Frame(6, 0, 0));
			put(7, new Frame(7, 0, 0));
			put(8, new Frame(8, 0, 0));
			put(9, new Frame(9, 0, 0));
			put(10, new LastFrame(10, 0, 0, 0, true, true, false));
		}
	};

	@Before
	public void init() {
		Collection<Frame> frames = mockFrames.values();
		LastFrame lastFrame = (LastFrame) mockFrames.get(10);
		Mockito.when(frameDao.getAllFrames()).thenReturn(frames);
		Mockito.when(frameDao.getLastFrame()).thenReturn(lastFrame);
	}

	@Test
	public void checkScoreWithStrikeBonus()  {
		// piersze 3 rzuty to: 10,2,3
		mockFrames.remove(1);
		mockFrames.put(1, new Frame(1, 10, 2));
		mockFrames.remove(2);
		mockFrames.put(2, new Frame(2, 3, 0));

		assertEquals(20, gameService.score());
 	}

	@Test
	public void checkScoreWithSpareBonus() {
		// piersze 3 rzuty to: 5,5,3
		mockFrames.remove(1);
		mockFrames.put(1, new Frame(1, 5, 5));
		mockFrames.remove(2);
		mockFrames.put(2, new Frame(2, 3, 0));

		assertEquals(16, gameService.score());
	}

	@Test
	public void checkLastRollWithStrikeBonus() {
		// ostatnie 3 rzuty to: 10,4,7
		mockFrames.remove(10);
		mockFrames.put(10, new LastFrame(10, 10, 4, 7, true, true, true));

		assertEquals(25, gameService.score());
	}

	@Test
	public void checkLastRollWithSpareBonus() {
		// ostatnie rzuty to: 5,5,8
		mockFrames.remove(10);
		mockFrames.put(10, new LastFrame(10, 5, 5, 8, true, true, true));

		assertEquals(18, gameService.score());
	}

	@Test
	public void checkLastRollWithNoBonus() {
		// ostatnie rzuty to: 3,1,0
		mockFrames.remove(10);
		mockFrames.put(10, new LastFrame(10, 3, 1, 0, true, true, false));

		assertEquals(4, gameService.score());
	}

	@Test
	public void checkingScoreWhenGameIsNotFinishedReturnsError() {
		// game is not finished yet
		mockFrames.remove(10);
		mockFrames.put(10, new LastFrame(10, 3, 1, 0, true, false, false));
		// then Error
		try {
			gameService.score();
		} catch (Error error) {
			assertEquals(error.getMessage() , gameNotFinishedError);
		}
	}

}
