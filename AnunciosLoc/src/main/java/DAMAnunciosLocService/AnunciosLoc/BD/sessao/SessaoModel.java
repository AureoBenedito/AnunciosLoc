package DAMAnunciosLocService.AnunciosLoc.BD.sessao;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "sessao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessaoModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "token", unique = true, nullable = false)
    protected String token;
    @Column(name = "user", unique = true)
    protected Long user;
    @Column(name = "fingerprint", unique = true)
    protected String fingerprint;

    @Column(name = "createdAt")
    private Timestamp createdAt;


    /*public String returnString () {
        return this.id +":"+ this.token +":"+this.user+":"+this.fingerprint;
    }

    public static SessaoModel parse (String objectString){
        String [] data = objectString.split(objectString);
        SessaoModel s = new SessaoModel();
        s.setId(new Long(data[0]));
        s.setToken(data[1]);
        s.setUser(new  Long(data[2]));
        s.setFingerprint(data[3]);
        return s;
    }*/


}
