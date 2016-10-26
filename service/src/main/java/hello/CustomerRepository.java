package hello;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CustomerRepository extends the MongoRepository interface and plugs in the type of values and id it
 * works with: Customer and String. Out-of-the-box, this interface comes with many operations,
 * including standard CRUD operations (create-read-update-delete).
 * <p>
 * You can define other queries as needed by simply declaring their method signature. In this case,
 * you add findByFirstName, which essentially seeks documents of type Customer and finds the one
 * that matches on firstName.
 * <p>
 * You also have findByLastName to find a list of people by last name.
 * <p>
 * In a typical Java application, you write a class that implements CustomerRepository and craft
 * the queries yourself. What makes Spring Data MongoDB so useful is the fact that you don’t have
 * to create this implementation. Spring Data MongoDB creates it on the fly when you run the application.
 *
 *
 *
 * TODO: This seems too abstract to me, at least play with some examples that show more of the magic.
 */

public interface CustomerRepository extends MongoRepository<Customer, String>
{

    public Customer findByFirstName(String firstName);

    public List<Customer> findByLastName(String lastName);

}
