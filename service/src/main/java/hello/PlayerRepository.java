package hello;

import com.wedgwoodtom.test.data.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String>
{
    public Player findByEmail(String email);

    public List<Player> findByLastName(String lastName);

}
