/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemo;

import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Address;
import model.Customer;
import model.Model;

/**
 *
 * @author pwn233
 */
public class EntityManagerDemo {
    private static int x = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Model m = new Model();
        OUTER:

        while (true) {
            System.out.println("Command : 1 : [printAllCustomer], 2 : [printCustomerByCity], 3 : [exit]");
            System.out.print("Input : ");
            x = sc.nextInt();
            sc.nextLine();
            switch (x) {
                case 1:
                    m.printAllCustomer();
                    break;
                case 2:
                    System.out.print("Input City name : ");
                    String city = sc.nextLine();
                    m.printCustomerByCity(city);
                    break;
                case 3:
                    break OUTER;
                default:
                    System.out.println("Wrong Input!");
                    break;
            }
        }
    }
    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
