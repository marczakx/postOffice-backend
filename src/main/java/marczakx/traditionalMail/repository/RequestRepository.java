package marczakx.traditionalMail.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import marczakx.traditionalMail.model.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request , Long> {

}
