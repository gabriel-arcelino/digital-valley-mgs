package br.ufc.russas.n2s.darwin.beans;

import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Lavínia Matoso
 */
public class UsuarioBeans implements Beans, Serializable{
    
	private static final long serialVersionUID = 7618752980671884090L;
	
	private long codUsuario;
    private long codUsuarioControleDeAcesso;
    private String nome;
    private String email;
    private String CPF;
    private List<EnumPermissao> permissoes;
    private boolean recebeEmail;
    
    public long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public List<EnumPermissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<EnumPermissao> permissoes) {
        this.permissoes = permissoes;
    }

    public long getCodUsuarioControleDeAcesso() {
        return codUsuarioControleDeAcesso;
    }

    public void setCodUsuarioControleDeAcesso(long codUsuarioControleDeAcesso) {
        this.codUsuarioControleDeAcesso = codUsuarioControleDeAcesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
    	return this.email;
    }
        
    public void setEmail(String email) {
    	this.email = email;
    }
    
    
    public boolean isRecebeEmail() {
		return recebeEmail;
	}
    
    public void setRecebeEmail(boolean recebeEmail) {
		this.recebeEmail = recebeEmail;
	}
    
    public String getCPF() {
		return CPF;
	}
    
    public void setCPF(String CPF) {
		this.CPF = CPF;
	}
    
    @Override
    public Object toBusiness() {
        UsuarioDarwin usuario = new UsuarioDarwin();
        if (this.getCodUsuario() > 0) {
            usuario.setCodUsuario(this.getCodUsuario());
        }
        if (this.getCodUsuarioControleDeAcesso()> 0) {
            usuario.setCodUsuarioControleDeAcesso(this.getCodUsuarioControleDeAcesso());
        }
        usuario.setNome(this.getNome());
        usuario.setEmail(this.getEmail());
        usuario.setPermissoes(this.getPermissoes());
        usuario.setRecebeEmail(this.isRecebeEmail());
        usuario.setCPF(this.getCPF());
        return usuario;
    }

    @Override
    public Beans toBeans(Object object) {
        if (object != null) {
            if (object instanceof UsuarioDarwin) {
                UsuarioDarwin usuario = (UsuarioDarwin) object;
                this.setCodUsuario(usuario.getCodUsuario());
                this.setPermissoes(usuario.getPermissoes());
                this.setCodUsuarioControleDeAcesso(usuario.getCodUsuarioControleDeAcesso());
                this.setNome(usuario.getNome());
                this.setEmail(usuario.getEmail());
                this.setRecebeEmail(usuario.isRecebeEmail());
                this.setCPF(usuario.getCPF());
                return this;
            } else {
                throw new IllegalArgumentException("Isso não é um usuário!");
            }
        } else {
            throw new NullPointerException("Usuário não pode ser vazio!");
        }  
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o != null) { 
    		return this.getCodUsuario() == ((UsuarioBeans) o).getCodUsuario();
    	} else {
    		return false;
    	}
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (int) (this.codUsuario ^ (this.codUsuario >>> 32));
        return hash;
    }
}
