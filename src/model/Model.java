/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author pwn233
 */
public class Model {
    
    public void createData() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Customer customer = new Customer(1L, "John", "Henry", "jh@mail.com"); 
        Address address = new Address(1L, "123/4 Viphavadee Rd.", "Bangkok", "TH", "10900"); 
        address.setCustomerFk(customer); 
        customer.setAddressId(address);
        Customer customer1 = new Customer(2L, "Marry", "Jane", "mj@mail.com"); 
        Address address1 = new Address(2L, "123/5 Viphavadee Rd.", "Bangkok", "TH", "10900"); 
        address1.setCustomerFk(customer1); 
        customer1.setAddressId(address1);
        Customer customer2 = new Customer(3L, "Peter", "Parker", "pp@mail.com"); 
        Address address2 = new Address(3L, "543/21 Fake Rd.", "Nonthaburi", "TH", "20900"); 
        address2.setCustomerFk(customer2); 
        customer2.setAddressId(address2);
        Customer customer3 = new Customer(4L, "Bruce", "Wayne", "bw@mail.com"); 
        Address address3 = new Address(4L, "678/90 Fake Rd.", "Pahtumthani", "TH", "30500"); 
        address3.setCustomerFk(customer3); 
        customer3.setAddressId(address3);
        em.getTransaction().begin();
        try {
            em.persist(customer);
            em.persist(address);
            em.flush();
            em.persist(customer1);
            em.persist(address1);
            em.flush();
            em.persist(customer2);
            em.persist(address2);
            em.flush();
            em.persist(customer3);
            em.persist(address3);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    public List<Customer> findAllCustomer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        List<Customer> customerList = (List<Customer>) em.createNamedQuery("Customer.findAll").getResultList();
        em.close();
        return customerList;
    }
    
    public void printAllCustomer() {
        List<Customer> custList = findAllCustomer();
        for(Customer cust : custList) {
            System.out.println("First name : "+cust.getFirstname());
            System.out.println("Last name : "+cust.getLastname());
            System.out.println("Email : "+cust.getEmail());
            System.out.println("Street : "+cust.getAddressId().getStreet());
            System.out.println("City : "+cust.getAddressId().getCity());
            System.out.println("Country : "+cust.getAddressId().getCountry());
            System.out.println("Zipcode : "+cust.getAddressId().getZipcode());
            System.out.println("------------------------------------------");
            System.out.println("------------------------------------------");
        }
    }
    
    /* brute force way*/
    /*
    public void printAllCustomer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Customer customer = em.find(Customer.class, 1L);
        Address address = em.find(Address.class, 1L);
        System.out.println("First name : "+customer.getFirstname());
        System.out.println("Last name : "+customer.getLastname());
        System.out.println("Email : "+customer.getEmail());
        System.out.println("Street : "+address.getStreet());
        System.out.println("City : "+address.getCity());
        System.out.println("Country : "+address.getCountry());
        System.out.println("Zipcode : "+address.getZipcode());
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        Customer customer1 = new Customer(2L, "Marry", "Jane", "mj@mail.com"); 
        Address address1 = new Address(2L, "123/5 Viphavadee Rd.", "Bangkok", "TH", "10900"); 
        System.out.println("First name : "+customer1.getFirstname());
        System.out.println("Last name : "+customer1.getLastname());
        System.out.println("Email : "+customer1.getEmail());
        System.out.println("Street : "+address1.getStreet());
        System.out.println("City : "+address1.getCity());
        System.out.println("Country : "+address1.getCountry());
        System.out.println("Zipcode : "+address1.getZipcode());
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        Customer customer2 = new Customer(3L, "Peter", "Parker", "pp@mail.com"); 
        Address address2 = new Address(3L, "543/21 Fake Rd.", "Nonthaburi", "TH", "20900"); 
        System.out.println("First name : "+customer2.getFirstname());
        System.out.println("Last name : "+customer2.getLastname());
        System.out.println("Email : "+customer2.getEmail());
        System.out.println("Street : "+address2.getStreet());
        System.out.println("City : "+address2.getCity());
        System.out.println("Country : "+address2.getCountry());
        System.out.println("Zipcode : "+address2.getZipcode());
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        Customer customer3 = new Customer(4L, "Bruce", "Wayne", "bw@mail.com"); 
        Address address3 = new Address(4L, "678/90 Fake Rd.", "Pahtumthani", "TH", "30500");
        System.out.println("First name : "+customer3.getFirstname());
        System.out.println("Last name : "+customer3.getLastname());
        System.out.println("Email : "+customer3.getEmail());
        System.out.println("Street : "+address3.getStreet());
        System.out.println("City : "+address3.getCity());
        System.out.println("Country : "+address3.getCountry());
        System.out.println("Zipcode : "+address3.getZipcode());
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
    }
    */
    public static List<Customer> findByTitle(String cityName) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        //String jpql = "SELECT c FROM Customer c WHERE c.addressId.city = ?1";
        String jpql = "SELECT c FROM Customer c WHERE c.addressId.city = :cityName";
        Query query = em.createQuery(jpql);
        //query.setParameter(1, cityName);
        query.setParameter("cityName", cityName);
        List<Customer> custList = (List<Customer>) query.getResultList();
        return custList;
    }
    public void printCustomerByCity(String city) {
        List<Customer> custList = findByTitle(city);
        for(Customer cust : custList) {
            System.out.println("First name : "+cust.getFirstname());
            System.out.println("Last name : "+cust.getLastname());
            System.out.println("Email : "+cust.getEmail());
            System.out.println("Street : "+cust.getAddressId().getStreet());
            System.out.println("City : "+cust.getAddressId().getCity());
            System.out.println("Country : "+cust.getAddressId().getCountry());
            System.out.println("Zipcode : "+cust.getAddressId().getZipcode());
            System.out.println("------------------------------------------");
            System.out.println("------------------------------------------");
        }
    }
}
