package hello;

import com.wedgwoodtom.test.data.Player;
import com.wedgwoodtom.test.data.PlayerRankings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by tompatterson on 11/6/16.
 */
public interface PlayerRankingsRepository extends MongoRepository<PlayerRankings, String>
{
}