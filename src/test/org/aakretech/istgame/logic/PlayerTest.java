package test.org.aakretech.istgame.logic;

import org.aakretech.istgame.logic.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player player;

    @Before
    public void setupNewPlayer() throws Exception {
        player = new Player("name");
    }

    @Test
    public void testGetPlayerInfo() {
        Assert.assertEquals(0, player.getScore());
        Assert.assertEquals("name", player.getName());
        Assert.assertEquals(true, player.isHuman());
    }

    @Test
    public void testResetScore() {
        Assert.assertEquals(0, player.getScore());
        player.addScore(75);
        Assert.assertEquals(75, player.getScore());
        player.resetScore();
        Assert.assertEquals(0, player.getScore());
    }

    @Test
    public void testChangeScore() {
        Assert.assertEquals(0, player.getScore());
        player.addScore(1);
        Assert.assertEquals(1, player.getScore());
        player.addScore(60);
        Assert.assertEquals(61, player.getScore());
        player.addScore(101);
        Assert.assertEquals(162, player.getScore());
        player.substractScore(1);
        Assert.assertEquals(161, player.getScore());
        player.substractScore(261);
        Assert.assertEquals(-100, player.getScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNegativeScoreException() {
        player.addScore(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractNegativeScoreException() {
        player.substractScore(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddZeroScoreException() {
        player.addScore(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractZeroScoreException() {
        player.substractScore(0);
    }
}