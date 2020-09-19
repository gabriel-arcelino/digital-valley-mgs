package br.ufc.russas.n2s.darwin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vw_aluno_curso")
public class VoAlunoCurso implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -1734326798528086965L;

	 @Id
	 @Column(name = "id_pessoa_usuario")
	 private long idPessoaUsuario;
	
	 @Column(name = "id_curso", nullable = true)
	 private Integer idCurso;
	 
	 @Column(name = "nome")
	 private String nomeAluno;
	 
	 @Column(name = "nome_curso", nullable = true)
	 private String nomeCurso;

	public long getIdPessoaUsuario() {
		return idPessoaUsuario;
	}

	public void setIdPessoaUsuario(long idPessoaUsuario) {
		this.idPessoaUsuario = idPessoaUsuario;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
	public VoAlunoCurso() {
		super();
	}

	public VoAlunoCurso(long idPessoaUsuario, Integer idCurso, String nomeAluno, String nomeCurso) {
		super();
		this.idPessoaUsuario = idPessoaUsuario;
		this.idCurso = idCurso;
		this.nomeAluno = nomeAluno;
		this.nomeCurso = nomeCurso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCurso;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VoAlunoCurso other = (VoAlunoCurso) obj;
		if (idCurso != other.idCurso)
			return false;
		return true;
	}
	 
}
