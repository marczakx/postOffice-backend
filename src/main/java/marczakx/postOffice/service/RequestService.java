package marczakx.postOffice.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marczakx.postOffice.model.Priority;
import marczakx.postOffice.model.Request;
import marczakx.postOffice.repository.RequestRepository;

@Service
public class RequestService {

	private RequestRepository requestRepository;
	private QueueService queueService;

	@Autowired
	public RequestService(RequestRepository requestRepository, QueueService queueService) {
		this.requestRepository = requestRepository;
		this.queueService = queueService;
	}

	public Request addRequest(String pseudonym, Priority priority) {
		Request request = new Request(pseudonym, priority, LocalDateTime.now());
		request=requestRepository.save(request);
		queueService.update();
		return request;
	}
}
