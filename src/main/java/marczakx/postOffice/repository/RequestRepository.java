package marczakx.postOffice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import marczakx.postOffice.model.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request , Long> {

}
