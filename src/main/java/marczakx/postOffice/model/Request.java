package marczakx.postOffice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String pseudonym;
	private Priority priority;
	private LocalDateTime time;

	public Request() {
	}

	public Request(Long id, String pseudonym, Priority priority, LocalDateTime time) {
		super();
		this.id = id;
		this.pseudonym = pseudonym;
		this.priority = priority;
		this.time = time;
	}

	public Request(String pseudonym, Priority priority, LocalDateTime time) {
		this.pseudonym = pseudonym;
		this.priority = priority;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
