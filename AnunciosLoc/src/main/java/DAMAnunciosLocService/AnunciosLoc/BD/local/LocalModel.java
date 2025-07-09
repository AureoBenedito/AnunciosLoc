package DAMAnunciosLocService.AnunciosLoc.BD.local;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="locais")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocalModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "nome", unique = true, nullable = false)
    private String nome;
    @Column(name = "latitude", unique = true, nullable = false)
    private Double latitude;
    @Column(name = "longitude", unique = true, nullable = false)
    private Double longitude;

    /*public String returnString () {
        return this.id +":"+ this.bi +":"+this.telefone+":"+this.role+":"+this.userId;
    }

    public static AdminModel parse (String objectString){
        String [] data = objectString.split(objectString);
        AdminModel a = new AdminModel();
        a.setId(new Long(data[0]));
        a.setBi(data[1]);
        a.setTelefone(data[2]);
        a.setRole(data[3]);
        a.setUserId(new Long(data[4]));
        return a;
    }*/

}
