package marczakx.traditionalMail.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import marczakx.traditionalMail.entity.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request , Long> {

}
