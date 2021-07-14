package marczakx.traditionalMail.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marczakx.traditionalMail.entity.Priority;
import marczakx.traditionalMail.entity.Request;
import marczakx.traditionalMail.repository.RequestRepository;

@Service
public class RequestService {

	private RequestRepository requestRepository;

	@Autowired
	public RequestService(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	public void addRequest(String pseudonym, Priority priority) {
		Request request = new Request(pseudonym,priority,LocalDateTime.now());
		requestRepository.save(request);
	}
}
