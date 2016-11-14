package com.wedgwoodtom.contest.ui;

import javax.annotation.PostConstruct;

import com.wedgwoodtom.contest.service.ContestManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.boot.VaadinAutoConfiguration;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VaadinUITests.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class VaadinUITests
{

    @Autowired
    CustomerRepository repository;

    @Mock
    private ContestManager contestManager;

    VaadinRequest vaadinRequest = Mockito.mock(VaadinRequest.class);
    CustomerEditor editor;
    ContestUI contestUI;

    @Before
    public void setup()
    {
        this.editor = new CustomerEditor(this.repository);
        this.contestUI = new ContestUI(this.repository, editor);

        // TODO: Setter or pass in via param?
        contestUI.setContestManager(contestManager);
    }

    @Test
    public void dummy()
    {
        // TODO: Come back and fix or delete these tests
    }

//    @Test
    public void shouldInitializeTheGridWithCustomerRepositoryData()
    {
        int customerCount = (int) this.repository.count();

        contestUI.init(this.vaadinRequest);

        then(contestUI.grid.getColumns()).hasSize(3);
        then(contestUI.grid.getContainerDataSource().getItemIds()).hasSize(customerCount);
    }

//    @Test
    public void shouldFillOutTheGridWithNewData()
    {
        int initialCustomerCount = (int) this.repository.count();
        this.contestUI.init(this.vaadinRequest);
        customerDataWasFilled(editor, "Marcin", "Grzejszczak");

        this.editor.save.click();

        then(contestUI.grid.getContainerDataSource().getItemIds()).hasSize(initialCustomerCount + 1);
        then((Customer) contestUI.grid.getContainerDataSource().lastItemId())
                .extracting("firstName", "lastName")
                .containsExactly("Marcin", "Grzejszczak");
    }

//    @Test
    public void shouldFilterOutTheGridWithTheProvidedLastName()
    {
        this.contestUI.init(this.vaadinRequest);
        this.repository.save(new Customer("Josh", "Long"));

        contestUI.listCustomers("Long");

        then(contestUI.grid.getContainerDataSource().getItemIds()).hasSize(1);
        then((Customer) contestUI.grid.getContainerDataSource().lastItemId())
                .extracting("firstName", "lastName")
                .containsExactly("Josh", "Long");
    }

//    @Test
    public void shouldInitializeWithInvisibleEditor()
    {
        this.contestUI.init(this.vaadinRequest);

        then(this.editor.isVisible()).isFalse();
    }

//    @Test
    public void shouldMakeEditorVisible()
    {
        this.contestUI.init(this.vaadinRequest);
        Object itemId = this.contestUI.grid.getContainerDataSource().getItemIds().iterator().next();

        this.contestUI.grid.select(itemId);

        then(this.editor.isVisible()).isTrue();
    }

    private void customerDataWasFilled(CustomerEditor editor, String firstName, String lastName)
    {
        this.editor.firstName.setValue(firstName);
        this.editor.lastName.setValue(lastName);
        editor.editCustomer(new Customer(firstName, lastName));
    }

    @Configuration
    @EnableAutoConfiguration(exclude = VaadinAutoConfiguration.class)
    static class Config
    {
        @Autowired
        CustomerRepository repository;

        @Mock
        private ContestManager contestManager;

        @PostConstruct
        public void initializeData()
        {
            this.repository.save(new Customer("Jack", "Bauer"));
            this.repository.save(new Customer("Chloe", "O'Brian"));
            this.repository.save(new Customer("Kim", "Bauer"));
            this.repository.save(new Customer("David", "Palmer"));
            this.repository.save(new Customer("Michelle", "Dessler"));
        }

        @Bean
        public ContestManager contestManager()
        {
            return contestManager;
        }


    }

}
