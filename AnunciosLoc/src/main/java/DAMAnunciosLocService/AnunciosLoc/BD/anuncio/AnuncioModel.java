package DAMAnunciosLocService.AnunciosLoc.BD.anuncio;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="anuncios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnuncioModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "titulo", unique = true, nullable = false)
    private String titulo;
    @Column(name = "descricao")
    private  String descricao;
    @Column(name = "user")
    private Long user;
    @Column(name = "local")
    private Long local;

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
