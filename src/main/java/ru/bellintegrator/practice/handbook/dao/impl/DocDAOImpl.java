package ru.bellintegrator.practice.handbook.dao.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.handbook.dao.DocDAO;
import ru.bellintegrator.practice.handbook.model.Document;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.print.Doc;
import java.util.List;

@Repository
public class DocDAOImpl implements DocDAO {

    private final EntityManager em;

    @Autowired
    public DocDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Document> all() {
        TypedQuery<Document> query = em.createQuery("SELECT d FROM Document d", Document.class);

        return query.getResultList();
    }

    @Override
    public Document loadByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(Document.class);

        Root<Document> documentRoot = criteriaQuery.from(Document.class);
        criteriaQuery.where(builder.equal(documentRoot.get("name"), name));

        TypedQuery<Document> query = em.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Document loadByCode(Integer code) {
        return em.find(Document.class, code);
    }

    @Override
    public Document load(Integer code, String name) throws NotFoundException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Document.class);

        Root<Document> document = criteriaQuery.from(Document.class);
        criteriaQuery.where(
                criteriaBuilder.equal(document.get("code"), code),
                criteriaBuilder.equal(document.get("name"), name)
        );

        TypedQuery<Document> query = em.createQuery(criteriaQuery);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NotFoundException("Документа с code = " + code + " и name = " + name + " не найдено");
        }
    }
}
