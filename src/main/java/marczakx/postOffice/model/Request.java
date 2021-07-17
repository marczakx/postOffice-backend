package marczakx.postOffice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String pseudonym;
	private Priority priority;
	private LocalDateTime time;

	public Request(String pseudonym, Priority priority, LocalDateTime time) {
		this.pseudonym = pseudonym;
		this.priority = priority;
		this.time = time;
	}


}
