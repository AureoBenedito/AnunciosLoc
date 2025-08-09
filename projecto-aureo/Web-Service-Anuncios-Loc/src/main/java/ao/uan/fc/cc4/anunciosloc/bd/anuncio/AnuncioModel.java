package ao.uan.fc.cc4.anunciosloc.bd.anuncio;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "anuncio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnuncioModel implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "titulo", unique = true, nullable = false)
    protected String titulo;
    @Column(name = "descricao", nullable = false)
    protected String descricao;
    @Column(name = "autor", nullable = false)
    protected Long autor;
    @Column(name = "local")
    protected String local;
}
