import com.wedgwoodtom.test.data.Player;
import org.junit.Test;

public class PlayerTest
{
    @Test
    public void testToString()
    {
        Player player = new Player("Tom", "Patterson", "tom@hell.com");
        System.out.println(player.toString());
    }



}
