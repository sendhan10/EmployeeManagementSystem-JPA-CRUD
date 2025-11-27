package org.example.dao;

import org.example.entity.Employee;
import org.example.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class EmployeeDAO {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public Employee save(Employee e) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        } finally {
            em.close();
        }
    }

    public Employee update(Employee e) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee merged = em.merge(e);
            em.getTransaction().commit();
            return merged;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE LOWER(e.name) = LOWER(:name)", Employee.class);
            q.setParameter("name", name);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findByDepartment(String dept) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE e.department = :dept", Employee.class);
            q.setParameter("dept", dept);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findBySalaryRange(int min, int max) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE e.salary BETWEEN :min AND :max", Employee.class);
            q.setParameter("min", min);
            q.setParameter("max", max);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e ORDER BY e.id", Employee.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee e = em.find(Employee.class, id);
            if (e != null) {
                em.remove(e);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try {
            Long c = em.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult();
            return c != null ? c : 0;
        } finally {
            em.close();
        }
    }
}
