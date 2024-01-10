package org.trainopia.pms.features.project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.trainopia.pms.features.project.Project;
import org.trainopia.pms.features.project.dto.ProjectDTO;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

    private EntityManager entityManager;

    @Autowired
    public ProjectDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        String countQuery = "SELECT COUNT(u) FROM Project u";
        Long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();
        List<ProjectDTO> result = entityManager.createQuery(
            "SELECT NEW org.trainopia.pms.features.project.dto.ProjectDTO( "
                + "p.id, p.createdAt, p.updatedAt, p.title, p.minAge, p.maxAge, p.price, p.location, p.projectDetails) "
                + "FROM Project p",
            ProjectDTO.class).setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Project> findAllWithExpenses() {
        return null;
    }

    @Override
    public Optional<Project> findById(int id) {
        List<Project> resultList = entityManager.createQuery(
            "FROM Project p left join fetch  p.projectExpenses join fetch  p.projectDetails where p.id=:id",
            Project.class).setParameter("id", id).getResultList();
        return resultList.size() != 0 ? Optional.of(resultList.get(0)) : Optional.empty();
    }

    @Override
    @Transactional
    public Project save(Project project) {
        entityManager.persist(project);
        return project;
    }

    @Override
    public void deleteById(int id) {

    }
}
