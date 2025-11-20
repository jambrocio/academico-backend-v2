package pe.edu.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.edu.university.dto.MatriculaDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalMessageService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.rabbitmq.url}")
    private String rabbitMqUrl;

    @Value("${services.kafka.url}")
    private String kafkaUrl;

    /**
     * Send matricula notification to external service.
     * On success, logs the response; on failure, logs the error but does not throw (graceful degradation).
     */
    public void sendMatriculaMessageRabbitMq(MatriculaDto matriculaDto) {
        try {
            log.info("Sending matricula message to {}: matriculaId={}", this.rabbitMqUrl, matriculaDto.getMatriculaId());
            
            // POST the DTO to external service
            Object response = restTemplate.postForObject(this.rabbitMqUrl, matriculaDto, Object.class);
            
            log.info("Message sent successfully. Response: {}", response);
        } catch (Exception e) {
            // Log error but don't throw; graceful degradation
            log.error("Error sending matricula message to external service (matriculaId={}): {}", 
                matriculaDto.getMatriculaId(), e.getMessage(), e);
        }
    }

    public void sendMatriculaMessageKafkaCreate(MatriculaDto matriculaDto) {
        try {

            System.out.println("Sending matricula message to Kafka service at " + this.kafkaUrl + "/create");
            //log.info("Sending matricula message to {}: matriculaId={}", this.kafkaUrl, matriculaDto.getMatriculaId());
            
            // POST the DTO to external service
            Object response = restTemplate.postForObject(this.kafkaUrl + "/create", matriculaDto, String.class);
            
            //log.info("Message sent successfully. Response: {}", response);
        } catch (Exception e) {
            // Log error but don't throw; graceful degradation
            log.error("Error sending matricula message to external service (matriculaId={}): {}", 
                matriculaDto.getMatriculaId(), e.getMessage(), e);
        }
    }
}
