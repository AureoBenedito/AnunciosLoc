package ao.uan.fc.cc4.anunciosloc.bd.infra;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// import java.sql.Timestamp;

@Entity
@Table(name = "infraestrutura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfraModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "endpoint", nullable = false, unique = true)
    protected String endpoint;
    @Column(name = "bonus", nullable = true)
    private int bonus = 0;
    @Column(name = "localName", nullable = true)
    protected String localName;
    @Column(name = "latitude", nullable = true)
    protected Float latitude;
    @Column(name = "longitude", nullable = true)
    protected Float longitude;
    @Column(name = "state", nullable = true)
    private int state = 0;
    @Column(name = "raio", nullable = true)
    private float raio;
    
}
