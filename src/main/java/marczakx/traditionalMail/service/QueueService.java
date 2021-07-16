package marczakx.traditionalMail.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marczakx.traditionalMail.entity.Priority;
import marczakx.traditionalMail.entity.Request;
import marczakx.traditionalMail.repository.RequestRepository;

@Service
public class QueueService {

	private Deque<QueueItem> queue;
	private Deque<Request> dequeIn;
	private RequestRepository requestRepository;

	@Autowired
	public QueueService(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
		update();
	}

	public Iterable<QueueItem> getList() {
		return queue.stream().filter(e -> e.getStartTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
	}

	public Long getTime(Long id) {
		var returnVal = Long.MIN_VALUE;
		var optional = queue.stream().filter(e -> e.getRequest().getId().equals(id)).findFirst();
		if (optional.isPresent()) {
			returnVal = LocalDateTime.now().until(optional.get().getStartTime(), ChronoUnit.SECONDS);
		}
		return returnVal;
	}

	public Long getTime(String pseudonym) {
		var returnVal = Long.MIN_VALUE;
		var optional = queue.stream().filter(e -> e.getRequest().getPseudonym().equals(pseudonym)).findFirst();
		if (optional.isPresent()) {
			returnVal = getTime(optional.get().getRequest().getId());
		}
		return returnVal;
	}

	public Long getNumber(Long id) {
		var returnVal = Long.MIN_VALUE;
		var optional = queue.stream().filter(e -> e.getRequest().getId().equals(id)).findFirst();
		if (optional.isPresent()) {
			returnVal = queue.stream().filter(e -> e.getStartTime().isAfter(LocalDateTime.now()))
					.filter(e -> e.getStartTime().isBefore(optional.get().getStartTime())).count();
		}
		return returnVal;
	}

	public Long getNumber(String pseudonym) {
		var returnVal = Long.MIN_VALUE;
		var optional = queue.stream().filter(e -> e.getRequest().getPseudonym().equals(pseudonym)).findFirst();
		if (optional.isPresent()) {
			returnVal = getNumber(optional.get().getRequest().getId());
		}
		return returnVal;
	}

	public void update() {
		queue = new LinkedList<>();
		dequeIn = StreamSupport.stream(requestRepository.findAll().spliterator(), false)
				.collect(Collectors.toCollection(LinkedList::new));
		sortByTime();
		createQueue();
	}

	private void createQueue() {
		while (!dequeIn.isEmpty()) {
			addToQueue(dequeIn.pollFirst());
		}
	}

	private void addToQueue(Request request) {
		removeItemsWithLowerPriority(request);
		var queueItem = new QueueItem();
		LocalDateTime endTimeOfTheQueue = getEndTimeOfTheQueue();
		queueItem.setRequest(request);
		queueItem.setTime(endTimeOfTheQueue);
		queue.add(queueItem);
	}

	private LocalDateTime getEndTimeOfTheQueue() {
		if (!queue.isEmpty()) {
			return queue.peekLast().getEndTime();
		}
		return LocalDateTime.MIN;
	}

	private void removeItemsWithLowerPriority(Request request) {
		if (comparePriorities(request, queue.peekLast())) {
			dequeIn.addFirst(queue.pollLast().getRequest());
			removeItemsWithLowerPriority(request);
		}
	}

	private boolean comparePriorities(Request request, QueueItem queueItem) {
		if (null == queueItem) {
			return false;
		}
		if (!hasHigherPriority(request.getPriority(), queueItem.getRequest().getPriority())) {
			return false;
		}
		if (!isEarlier(request.getTime(), queueItem.getStartTime())) {
			return false;
		}
		return true;
	}

	private boolean isEarlier(LocalDateTime time1, LocalDateTime time2) {
		if (time1.compareTo(time2) < 0) {
			return true;
		}
		return false;
	}

	private boolean hasHigherPriority(Priority priority1, Priority priority2) {
		if (priority1.equals(Priority.VIP) && priority2.equals(Priority.NORMAL)) {
			return true;
		}
		if (priority1.equals(Priority.SUDDEN) && priority2.equals(Priority.NORMAL)) {
			return true;
		}
		if (priority1.equals(Priority.SUDDEN) && priority2.equals(Priority.VIP)) {
			return true;
		}
		return false;
	}

	private void sortByTime() {
		dequeIn = dequeIn.stream().sorted((o1, o2) -> o1.getTime().compareTo(o2.getTime()))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public Deque<QueueItem> get() {
		return queue;
	}
}