package dev.dstankovic.springdemo.dao;

import dev.dstankovic.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {

        // get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // create a query (sort by last name)
        Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);

        // get the list of customers from the query
        List<Customer> customers = query.getResultList();

        // return the list of retrieved customers
        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {

        // get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // save or update the customer, depending on whether the primary key exists
        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int id) {

        // get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // retrieve customer
        Customer customer = session.get(Customer.class, id);

        return customer;
    }

    @Override
    public void deleteCustomer(int id) {

        // get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // delete object with primary key
        Query query = session.createQuery("delete from Customer where id=:id");
        query.setParameter("id", id);

        query.executeUpdate();
    }
}
