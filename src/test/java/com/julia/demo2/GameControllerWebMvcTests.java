package com.julia.demo2;

import com.julia.demo2.Controller.GameController;
import com.julia.demo2.Dao.FakeFrameDaoImpl;
import com.julia.demo2.Entity.Frame;
import com.julia.demo2.Entity.LastFrame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GameController.class, secure = false)
@ContextConfiguration(classes=Main.class)
public class GameControllerWebMvcTests {

//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private FakeFrameDaoImpl fakeFrameDaoImpl;
//
//
//
//
//	Map<Integer, Frame> mockFrames = new HashMap<Integer, Frame>() {
//		{
//			put(1, new Frame(1, 10, 2));
//			put(2, new Frame(2, 3, 0));
//			put(3, new Frame(3, 0, 0));
//			put(4, new Frame(4, 0, 0));
//			put(5, new Frame(5, 0, 0));
//			put(6, new Frame(7, 0, 0));
//			put(7, new Frame(8, 0, 0));
//			put(8, new Frame(9, 0, 0));
//			put(10, new LastFrame(10, 0, 0, 0));
//		}
//	};
//
//
//	@Test
//	public void checkScoreWithStrikeBonus() throws Exception {
//		// stan bazy zmockowac. piersze 3 rzuty to: 10,2,3
//		Mockito.when(fakeFrameDaoImpl.getAllFrames()).thenReturn((Collection<Frame>) mockFrames);
//		Mockito.when(fakeFrameDaoImpl.getLastFrame()).thenReturn((LastFrame) mockFrames.get(10));
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/game/score/");
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		System.out.println(result.getResponse());
//
//		// expected score = 20
//		int expected = 20;
//
//		assertEquals(result.getResponse(), expected);
//	}
//
//    @Test
//    public void checkScoreWithSpareBonus() {
//        // stan bazy zmockowac. piersze 3 rzuty to: 5,5,3
//        // expected score = 16
//    }
//
//    @Test
//    public void checkLastRollWithStrikeBonus() {
//        // stan bazy zmockowac. piersze 3 rzuty to: 10,4,7
//        // expected score = 32
//    }
//
//    @Test
//    public void checkLastRollWithSpareBonus() {
//        // stan bazy zmockowac. piersze 3 rzuty to: 5,5,3
//        // expected score = 16
//    }
//
//    @Test
//    public void checkLastRollWithNoBonus() {
//        // stan bazy zmockowac. piersze 3 rzuty to: 3,1,1
//        // expected score = 5
//    }
//
//    @Test
//    public void checkingScoreWhenGameIsNotFinishedReturnsError() {
//        // when we call
//        // http://localhost:8080/game/score
//        // then
//        // Error:"Game is not finished yet. Score is available only after all rolls are done."
//    }
//
//    @Test
//    public void roll() {
//
//    }



}
