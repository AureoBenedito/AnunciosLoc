package ao.uan.fc.cc4.anunciosloc.bd.user;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true, nullable = false)
    protected String email;
    @Column(name = "password", nullable = true)
    protected String password;
    @Column(name = "nome")
    protected String nome;
    @Column(name = "genero")
    protected String genero;
    @Column(name = "telefone", nullable = false)
    private  String telefone;
    @Column(name = "tipo", nullable = false)
    protected int tipo;

}
