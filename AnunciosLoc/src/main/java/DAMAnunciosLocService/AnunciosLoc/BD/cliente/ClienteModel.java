package DAMAnunciosLocService.AnunciosLoc.BD.cliente;

import javax.persistence.*;

import DAMAnunciosLocService.AnunciosLoc.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table( name = "cliente" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClienteModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "points")
    protected Integer points;
    @Column(name = "state")
    protected  Integer state;
    @Column(name = "info")
    private String info;
    @Column(name = "userId")
    protected Long userId;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    public String returnString () {
        return this.id +":"+ this.points +":"+this.state+":"+this.info+":"+this.userId;
    }

    public static ClienteModel parse (String objectString){
        String [] data = objectString.split(objectString);
        ClienteModel a = new ClienteModel();
        a.setId(new Long(data[0]));
        a.setPoints(Utils.toInteger(data[1]));
        a.setState(Utils.toInteger(data[2]));
        a.setInfo(data[3]);
        a.setUserId(new Long(data[4]));
        return a;
    }


}
