package marczakx.traditionalMail.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import marczakx.traditionalMail.entity.Priority;
import marczakx.traditionalMail.entity.Request;
import marczakx.traditionalMail.service.QueueService;
import marczakx.traditionalMail.service.QueueItem;
import marczakx.traditionalMail.service.RequestService;

@RestController
@CrossOrigin
public class WebController {

	private RequestService requestService;
	private QueueService queueService;

	@Autowired
	public WebController(RequestService requestService, QueueService queueService) {
		this.requestService = requestService;
		this.queueService = queueService;
	}
	@GetMapping("/add/{pseudonym}")
	public Request adds(@PathVariable String pseudonym) {
		return requestService.addRequest(pseudonym, Priority.NORMAL);
	}

	@GetMapping("/vip/add/{pseudonym}")
	public Request addVip(@PathVariable String pseudonym) {
		return requestService.addRequest(pseudonym, Priority.VIP);
	}

	@GetMapping("/sudden/add/{pseudonym}")
	public Request addSudden(@PathVariable String pseudonym) {
		return requestService.addRequest(pseudonym, Priority.SUDDEN);
	}

	@GetMapping("/getTime/pseudonym/{pseudonym}")
	public Long getTime(@PathVariable String pseudonym) {
		return queueService.getTime(pseudonym);
	}

	@GetMapping("/getTime/id/{id}")
	public Long getTime(@PathVariable Long id) {
		return queueService.getTime(id);
	}

	@GetMapping("/getNumber/pseudonym/{pseudonym}")
	public Long getNumber(@PathVariable String pseudonym) {
		return queueService.getNumber(pseudonym);
	}

	@GetMapping("/getNumber/id/{id}")
	public Long getNumber(@PathVariable Long id) {
		return queueService.getNumber(id);
	}
	@GetMapping("/queue")
	public Iterable<QueueItem> getList() {
		return queueService.getList();
	}

}
