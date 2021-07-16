package marczakx.traditionalMail.service;

import java.time.LocalDateTime;

import marczakx.traditionalMail.entity.Priority;
import marczakx.traditionalMail.entity.Request;

public class QueueItem {
	private final int SUDDEN_DELAY_IN_SECONDS = 40;
	private final int NORMAL_DELAY_IN_SECONDS = 20;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Request request;

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setTime(LocalDateTime endTimeOfTheQueue) {
		LocalDateTime time = endTimeOfTheQueue;
		if (endTimeOfTheQueue.compareTo(getRequest().getTime()) < 0) {
			time = getRequest().getTime();
		}
		setStartTime(time);
		if (Priority.SUDDEN.equals(getRequest().getPriority())) {
			time = time.plusSeconds(SUDDEN_DELAY_IN_SECONDS);
		} else {
			time = time.plusSeconds(NORMAL_DELAY_IN_SECONDS);
		}
		setEndTime(time);
	}

}