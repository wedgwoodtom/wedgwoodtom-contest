package hello;

import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContestRepository extends MongoRepository<Contest, String>
{
    public Contest findByTitle(String title);

}

