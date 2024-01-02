package org.trainopia.pms.features.project;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }



    @Override
    public Project findById(int id) {
        Optional<Project> result = projectRepository.findById(id);
        Project project = null;
        if (result.isPresent()) {
            project = result.get();
        } else {
            throw new AppException("findById",this.getClass().getName(),"Project with id: " + id + " couldn't be found", CommonError.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return project;
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findAllWithExpenses() {
        return projectRepository.findAllWithExpenses();
    }

    @Override
    public Project findWithExpensesById(int id) {
//        throw new ProjectNotFoundException("Project Not Found with this id "+id);
        return projectRepository.findOneWithExpenses(id);
    }

    @Override
    public Project update(Project project) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
