package marczakx.postOffice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import org.mockito.junit.jupiter.MockitoExtension;

import marczakx.postOffice.model.Priority;
import marczakx.postOffice.model.Request;
import marczakx.postOffice.repository.RequestRepository;

@ExtendWith(MockitoExtension.class)
public class QueueServiceTests {

	@Mock
	RequestRepository requestRepository;
	@InjectMocks
	QueueService queneService;

	@Test
	void testOrder() {
		var requests = new ArrayList<Request>();
		requests.add(Request.builder().pseudonym("r1").priority(Priority.NORMAL)/**/
				.time(LocalDateTime.MIN).build());
		requests.add(Request.builder().pseudonym("r2").priority(Priority.NORMAL)/**/
				.time(LocalDateTime.MIN.plusSeconds(2)).build());
		requests.add(Request.builder().pseudonym("r3").priority(Priority.VIP)/**/
				.time(LocalDateTime.MIN.plusSeconds(3)).build());
		requests.add(Request.builder().pseudonym("r4").priority(Priority.SUDDEN)/**/
				.time(LocalDateTime.MIN.plusSeconds(5)).build());
		requests.add(Request.builder().pseudonym("r5").priority(Priority.VIP)/**/
				.time(LocalDateTime.MIN.plusSeconds(6)).build());
		requests.add(Request.builder().pseudonym("r6").priority(Priority.SUDDEN)/**/
				.time(LocalDateTime.MIN.plusSeconds(7)).build());
		requests.add(Request.builder().pseudonym("r7").priority(Priority.NORMAL)/**/
				.time(LocalDateTime.MIN.plusSeconds(500)).build());
		requests.add(Request.builder().pseudonym("r8").priority(Priority.NORMAL)/**/
				.time(LocalDateTime.MIN.plusSeconds(501)).build());
		requests.add(Request.builder().pseudonym("r9").priority(Priority.SUDDEN)/**/
				.time(LocalDateTime.MIN.plusSeconds(502)).build());
		requests.add(Request.builder().pseudonym("r10").priority(Priority.VIP)/**/
				.time(LocalDateTime.MIN.plusSeconds(503)).build());

		doReturn(requests).when(requestRepository).findAll();
		queneService.update();
		var quene = queneService.get().iterator();
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r1");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r4");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r6");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r3");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r5");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r2");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r7");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r9");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r10");
		assertThat(quene.next().getRequest().getPseudonym()).isEqualTo("r8");
		assertThat(quene.hasNext()).isEqualTo(false);
	}

	@Test
	void testGetTimeAndGetNumber() {
		var requests = new ArrayList<Request>();
		requests.add(new Request(1L, "r1", Priority.NORMAL, LocalDateTime.now().minusSeconds(11)));
		requests.add(new Request(2L, "r2", Priority.NORMAL, LocalDateTime.now().minusSeconds(9)));
		requests.add(new Request(3L, "r3", Priority.VIP, LocalDateTime.now().minusSeconds(6)));
		requests.add(new Request(4L, "r4", Priority.SUDDEN, LocalDateTime.now().minusSeconds(5)));
		requests.add(new Request(5L, "r5", Priority.NORMAL, LocalDateTime.now().plusSeconds(150)));

		doReturn(requests).when(requestRepository).findAll();
		queneService.update();
		assertThat(queneService.getTime(1L)).isEqualTo(-11L);
		assertThat(queneService.getTime(2L)).isEqualTo(68L);
		assertThat(queneService.getTime(3L)).isEqualTo(48L);
		assertThat(queneService.getTime(4L)).isEqualTo(8L);
		assertThat(queneService.getTime(5L)).isEqualTo(149L);

		assertThat(queneService.getTime("r2")).isEqualTo(68L);

		assertThat(queneService.getNumber(1L)).isEqualTo(0);
		assertThat(queneService.getNumber(2L)).isEqualTo(2);
		assertThat(queneService.getNumber(3L)).isEqualTo(1);
		assertThat(queneService.getNumber(4L)).isEqualTo(0);
		assertThat(queneService.getNumber(5L)).isEqualTo(3);

		assertThat(queneService.getNumber("r2")).isEqualTo(2);

	}
}
